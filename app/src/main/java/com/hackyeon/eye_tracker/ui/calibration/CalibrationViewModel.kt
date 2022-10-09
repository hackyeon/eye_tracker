package com.hackyeon.eye_tracker.ui.calibration

import androidx.lifecycle.ViewModel
import com.hackyeon.eye_tracker.calibration.CalibrationListener
import com.hackyeon.eye_tracker.util.HLog

class CalibrationViewModel: ViewModel(), CalibrationListener {


    override fun onReady() {

    }

    override fun onStartCalibration() {
    }

    private fun d(msg: Any?) {
        HLog.d("## [${this.javaClass.simpleName}] ## $msg")
    }
}