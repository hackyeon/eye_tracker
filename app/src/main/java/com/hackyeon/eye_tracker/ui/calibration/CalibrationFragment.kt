package com.hackyeon.eye_tracker.ui.calibration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hackyeon.eye_tracker.calibration.CalibrationListener
import com.hackyeon.eye_tracker.databinding.CalibrationFragmentBinding
import com.hackyeon.eye_tracker.ui.BaseFragment
import com.hackyeon.eye_tracker.util.HLog

class CalibrationFragment: BaseFragment() {
    private lateinit var binding: CalibrationFragmentBinding
    private val cViewModel: CalibrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = CalibrationFragmentBinding.inflate(inflater, container, false).apply {
            calibrationView.setCalibrationListener(cViewModel)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun d(msg: Any?) {
        HLog.d("## [${this.javaClass.simpleName}] ## $msg")
    }

}