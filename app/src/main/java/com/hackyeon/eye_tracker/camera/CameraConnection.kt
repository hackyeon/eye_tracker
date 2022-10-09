package com.hackyeon.eye_tracker.camera

import android.app.Activity
import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.concurrent.futures.await
import androidx.fragment.app.FragmentActivity
import com.hackyeon.eye_tracker.MainActivity
import com.hackyeon.eye_tracker.util.extension.getAspectRatio
import kotlinx.coroutines.*

object CameraConnection {
    private var qualityList: List<Quality> = emptyList()
    private var videoCapture: VideoCapture<Recorder>? = null

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

}