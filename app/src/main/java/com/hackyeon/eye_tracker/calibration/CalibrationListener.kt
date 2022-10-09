package com.hackyeon.eye_tracker.calibration

import com.hackyeon.eye_tracker.calibration.data.CoordinateItem

interface CalibrationListener {

    fun onReady()
    fun onStartCalibration()

    fun onItemChanged(item: CoordinateItem)
}