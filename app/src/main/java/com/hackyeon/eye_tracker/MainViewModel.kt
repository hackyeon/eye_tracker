package com.hackyeon.eye_tracker

import androidx.camera.video.*
import androidx.core.util.Consumer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hackyeon.eye_tracker.ui.calibration.data.CalibrationMode
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

    // next recording
    enum class NextRecording { CALIBRATION, ANIMATION; }
    private val _nextRecording = MutableLiveData<NextRecording>(NextRecording.CALIBRATION)
    val nextRecording: LiveData<NextRecording> = _nextRecording
    fun setNextRecording(value: NextRecording) = _nextRecording.postValue(value)


    /**
     * 녹화와 관련된 데이터를 초기화한다
     */
    fun clearData() {
        _nextRecording.postValue(NextRecording.CALIBRATION)
    }
}
