package com.hackyeon.eye_tracker.repository

import com.hackyeon.eye_tracker.KEY_ANIMATION_SPEED
import com.hackyeon.eye_tracker.KEY_CALIBRATION_INTERVAL
import com.hackyeon.eye_tracker.KEY_CALIBRATION_MODE
import com.hackyeon.eye_tracker.ui.animation.AnimationConfig
import com.hackyeon.eye_tracker.ui.calibration.CalibrationConfig
import com.hackyeon.eye_tracker.ui.calibration.data.CalibrationMode
import com.hackyeon.eye_tracker.util.manager.PreferenceManager

class EyeTrackerRepository {

    // preference get
    fun getCalibrationMode() = CalibrationMode.intToMode(PreferenceManager.getInt(KEY_CALIBRATION_MODE, CalibrationMode.FULL.value))
    fun getCalibrationInterval() = PreferenceManager.getLong(KEY_CALIBRATION_INTERVAL, CalibrationConfig.CALIBRATION_DELAY)
    fun getAnimationSpeed() = PreferenceManager.getInt(KEY_ANIMATION_SPEED, AnimationConfig.DEFAULT_ANIMATION_SPEED)

    // preference set
    fun setCalibrationMode(value: CalibrationMode?) = PreferenceManager.setInt(KEY_CALIBRATION_MODE, CalibrationMode.modeToInt(value?: CalibrationMode.FULL))
    fun setCalibrationInterval(value: Long?) = PreferenceManager.setLong(KEY_CALIBRATION_INTERVAL, value?: CalibrationConfig.CALIBRATION_DELAY)
    fun setAnimationSpeed(value: Int?) = PreferenceManager.setInt(KEY_ANIMATION_SPEED, value?: AnimationConfig.DEFAULT_ANIMATION_SPEED)
}