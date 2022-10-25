package com.hackyeon.eye_tracker.ui.calibration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.ui.calibration.data.CalibrationMode
import com.hackyeon.eye_tracker.ui.calibration.data.CoordinateItem
import com.hackyeon.eye_tracker.camera.CameraConnection
import com.hackyeon.eye_tracker.camera.CameraListener
import com.hackyeon.eye_tracker.databinding.CalibrationFragmentBinding
import com.hackyeon.eye_tracker.ui.base.BaseFragment
import com.hackyeon.eye_tracker.ui.base.BaseRecordingFragment
import com.hackyeon.eye_tracker.util.HLog

class CalibrationFragment: BaseRecordingFragment() {
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

    override val cameraListener = object: CameraListener {
        override fun onRecordingStart() {
            binding.calibrationView.startCalibration()
        }
    }

    private val calibrationListener = object: CalibrationListener {
        override fun getCalibrationMode(): CalibrationMode = viewModel.calibrationMode.value?: CalibrationMode.FULL
        override fun onReady() {
            CameraConnection.resetCalibration()
            CameraConnection.startRecording(requireActivity())
        }
        override fun onItemChanged(item: CoordinateItem) = CameraConnection.onCalibrationItemChanged(item)
        override fun onCalibrationFinished() {
            CameraConnection.stopRecording()
            viewModel.setNextRecording(MainViewModel.NextRecording.ANIMATION)
            findNavController().navigateUp()
        }

        override fun getCalibrationInterval(): Long? = viewModel.calibrationInterval.value
    }

    private fun d(msg: Any?) {
        HLog.d("## [${this.javaClass.simpleName}] ## $msg")
    }

}