package com.hackyeon.eye_tracker.calibration

import com.hackyeon.eye_tracker.calibration.data.CalibrationMode
import com.hackyeon.eye_tracker.calibration.data.CoordinateItem

interface CalibrationListener {

    fun getCalibrationMode(): CalibrationMode

    fun onReady()
    fun onItemChanged(item: CoordinateItem)
    fun onCalibrationFinished()
}