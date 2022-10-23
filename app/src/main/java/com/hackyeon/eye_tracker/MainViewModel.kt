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

}
