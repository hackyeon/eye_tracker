package com.hackyeon.eye_tracker

import android.Manifest
import android.os.Build
import androidx.camera.core.CameraSelector
import androidx.camera.video.*
import androidx.core.util.Consumer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.hackyeon.eye_tracker.calibration.data.CalibrationMode
import com.hackyeon.eye_tracker.repository.EyeTrackerRepository

class MainViewModel: ViewModel() {
    private val repository = EyeTrackerRepository()

    // setting
    private val _calibrationMode = MutableLiveData<CalibrationMode>(repository.getCalibrationMode())
    val calibrationMode: LiveData<CalibrationMode> = _calibrationMode
    fun setCalibrationMode(value: Boolean) {
        val mode = if(value) CalibrationMode.HALF else CalibrationMode.FULL
        _calibrationMode.postValue(mode)
    }
    fun saveSetting() {
        repository.setCalibrationMode(calibrationMode.value)
    }

    companion object {
        const val DEFAULT_ANIMATION_SPEED = 1
        const val DEFAULT_FLASH_TIME = 1
    }
    val animationSpeed = MutableLiveData<Int>()
    val flashTime = MutableLiveData<Int>()

    var videoCapture: VideoCapture<Recorder>? = null

    var currentRecording: Recording? = null

    var recordingState: VideoRecordEvent? = null

    val captureListener = Consumer<VideoRecordEvent> { event ->
        if(event !is VideoRecordEvent.Status) recordingState = event

    }

    fun stopRecording() {
        if(currentRecording == null || recordingState is VideoRecordEvent.Finalize) {
            return
        }
        currentRecording?.stop()
        currentRecording = null
    }


}
