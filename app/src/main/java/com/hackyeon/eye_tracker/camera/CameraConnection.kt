package com.hackyeon.eye_tracker.camera

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.camera.view.PreviewView
import androidx.concurrent.futures.await
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import androidx.fragment.app.FragmentActivity
import com.hackyeon.eye_tracker.ui.calibration.data.CoordinateItem
import com.hackyeon.eye_tracker.camera.ext.getAspectRatio
import com.hackyeon.eye_tracker.repository.api.req.CalibrationData
import com.hackyeon.eye_tracker.util.HLog
import com.hackyeon.eye_tracker.util.TimeUtil
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

object CameraConnection {
    private var mListener: CameraListener? = null
    fun setListener(listener: CameraListener) {
        this.mListener = listener
    }
    fun removeListener() {
        this.mListener = null
    }

    private var qualityList: List<Quality> = emptyList()
    private var videoCapture: VideoCapture<Recorder>? = null

    private var currentRecording: Recording? = null
    private var recordingState: VideoRecordEvent? = null

    private val captureListener = Consumer<VideoRecordEvent> { event ->
        if(event is VideoRecordEvent.Start) mListener?.onRecordingStart()
        recordingState = event
    }

    // for calibration
    private val calibrationDataList = mutableListOf<CalibrationData>()
    fun resetCalibration() {
        calibrationDataList.clear()
    }
    fun onCalibrationItemChanged(item: CoordinateItem) {
        val nano = recordingState?.recordingStats?.recordedDurationNanos?: 0L
        val time = TimeUtil.calculateTime(nano)
        calibrationDataList.add(CalibrationData(item.x, item.y, time))
    }

    /**
     * 녹화 종료
     */
    fun stopRecording() {
        if(currentRecording == null || recordingState is VideoRecordEvent.Finalize) return
        currentRecording?.stop()
        currentRecording = null
    }

    /**
     * 녹화 시작
     * @param context context
     */
    fun startRecording(context: Context) {
        if(recordingState == null || recordingState is VideoRecordEvent.Finalize) {
            val name = SimpleDateFormat(CameraConfig.DATE_FORMAT, Locale.KOREA)
                .format(System.currentTimeMillis()) + CameraConfig.VIDEO_TYPE

            val contentValues = ContentValues().apply {
                put(MediaStore.Video.Media.DISPLAY_NAME, name)
            }

            val mediaStoreOutput = MediaStoreOutputOptions.Builder(
                context.contentResolver,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            )
                .setContentValues(contentValues)
                .build()

            currentRecording = videoCapture?.output
                ?.prepareRecording(context, mediaStoreOutput)
                ?.start(ContextCompat.getMainExecutor(context), captureListener)
        }
    }



    /**
     * 카메라를 엑티비티에 바인딩한다
     * @param activity 바인딩할 엑티비티
     * @param previewUI 프리뷰로 사용할 뷰
     */
    suspend fun bindCamera(activity: FragmentActivity, previewUI: PreviewView) = withContext(Dispatchers.Main) {
        qualityList = getQuality(activity)
        if(qualityList.isEmpty()) return@withContext
        // todo

        val provider = ProcessCameraProvider.getInstance(activity).await()
        val quality = qualityList.first()
        val qualitySelector = QualitySelector.from(quality)

        val preview = Preview.Builder()
            .setTargetAspectRatio(quality.getAspectRatio(quality))
            .build()
            .apply { setSurfaceProvider(previewUI.surfaceProvider) }

        val recorder = Recorder.Builder()
            .setQualitySelector(qualitySelector)
            .build()

        videoCapture = VideoCapture.withOutput(recorder)

        try {
            provider.unbindAll()
            provider.bindToLifecycle(
                activity,
                CameraSelector.DEFAULT_FRONT_CAMERA,
                videoCapture,
                preview
            )
        } catch (e: Exception) {
            // todo
        }
    }
    /**
     * 카메라의 성능을 가져온다
     * @param activity 카메라를 바인딩할 엑티비티
     */
    private suspend fun getQuality(activity: FragmentActivity): List<Quality> = withContext(Dispatchers.Main) {
        val provider = ProcessCameraProvider.getInstance(activity).await()
        provider.unbindAll()
        try {
            if(provider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)) {
                val camera = provider.bindToLifecycle(activity, CameraSelector.DEFAULT_FRONT_CAMERA)
                QualitySelector.getSupportedQualities(camera.cameraInfo)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * 카메라를 종료한다
     * @param activity 카메라가 바인딩된 엑티비티
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun closeCamera(activity: FragmentActivity) = GlobalScope.launch {
        unBindCamera(activity)
        clearData()
    }
    /**
     * 카메라 바인딩을 해제한다
     * @param activity 바안딩된 엑티비티
     */
    private suspend fun unBindCamera(activity: FragmentActivity) = withContext(Dispatchers.Main) {
        val provider = ProcessCameraProvider.getInstance(activity).await()
        provider.unbindAll()
    }
    /**
     * 데이터 초기화
     */
    private suspend fun clearData() = withContext(Dispatchers.IO) {
        qualityList = emptyList()
        videoCapture = null
    }

    private fun d(msg: Any?) {
        HLog.d("## [${this.javaClass.simpleName}] ## $msg")
    }

}