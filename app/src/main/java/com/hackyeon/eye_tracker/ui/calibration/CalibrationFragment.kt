package com.hackyeon.eye_tracker.ui.calibration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.calibration.CalibrationListener
import com.hackyeon.eye_tracker.calibration.data.CoordinateItem
import com.hackyeon.eye_tracker.camera.CameraConnection
import com.hackyeon.eye_tracker.camera.CameraListener
import com.hackyeon.eye_tracker.databinding.CalibrationFragmentBinding
import com.hackyeon.eye_tracker.ui.BaseFragment
import com.hackyeon.eye_tracker.util.HLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalibrationFragment: BaseFragment() {
    private lateinit var binding: CalibrationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = CalibrationFragmentBinding.inflate(inflater, container, false).apply {
            calibrationView.setCalibrationListener(calibrationListener)
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CameraConnection.setListener(cameraListener)
    }

    override fun onDestroy() {
        CameraConnection.removeListener()
        super.onDestroy()
    }

    private val cameraListener = object: CameraListener {
        override fun onRecordingStart() {
            binding.calibrationView.startCalibration()
        }
    }

    private val calibrationListener = object: CalibrationListener {
        override fun onReady() {
            CameraConnection.resetCalibration()
            CameraConnection.startRecording(requireActivity())
        }
        override fun onItemChanged(item: CoordinateItem) = CameraConnection.onCalibrationItemChanged(item)
        override fun onCalibrationFinished() {
            CameraConnection.stopRecording()
            // todo state
            navigate(R.id.actionToPrepareFragment)
        }
    }

    private fun d(msg: Any?) {
        HLog.d("## [${this.javaClass.simpleName}] ## $msg")
    }

}