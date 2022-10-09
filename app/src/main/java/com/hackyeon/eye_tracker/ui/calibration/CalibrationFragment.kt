package com.hackyeon.eye_tracker.ui.calibration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.calibration.CalibrationListener
import com.hackyeon.eye_tracker.calibration.data.CoordinateItem
import com.hackyeon.eye_tracker.camera.CameraConnection
import com.hackyeon.eye_tracker.databinding.CalibrationFragmentBinding
import com.hackyeon.eye_tracker.ui.BaseFragment
import com.hackyeon.eye_tracker.util.HLog

class CalibrationFragment: BaseFragment(), CalibrationListener {
    private lateinit var binding: CalibrationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = CalibrationFragmentBinding.inflate(inflater, container, false).apply {
            calibrationView.setCalibrationListener(this@CalibrationFragment)
        }
        return binding.root
    }

    override fun onReady() = Unit

    override fun onStartCalibration() {
        CameraConnection.resetCalibration()
        CameraConnection.startRecording(requireActivity())
    }
    override fun onItemChanged(item: CoordinateItem) = CameraConnection.onCalibrationItemChanged(item)
    override fun onCalibrationFinished() {
        CameraConnection.stopRecording()
        // todo state
        navigate(R.id.actionToPrepareFragment)
    }

    private fun d(msg: Any?) {
        HLog.d("## [${this.javaClass.simpleName}] ## $msg")
    }

}