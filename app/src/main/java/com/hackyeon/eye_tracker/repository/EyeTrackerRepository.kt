package com.hackyeon.eye_tracker.repository

import com.hackyeon.eye_tracker.KEY_CALIBRATION_MODE
import com.hackyeon.eye_tracker.calibration.data.CalibrationMode
import com.hackyeon.eye_tracker.util.manager.PreferenceManager

class EyeTrackerRepository {




    // preference
    fun getCalibrationMode() = CalibrationMode.intToMode(PreferenceManager.getInt(KEY_CALIBRATION_MODE, CalibrationMode.FULL.value))

    fun setCalibrationMode(value: CalibrationMode?) = PreferenceManager.setInt(KEY_CALIBRATION_MODE, CalibrationMode.modeToInt(value?: CalibrationMode.FULL))

}