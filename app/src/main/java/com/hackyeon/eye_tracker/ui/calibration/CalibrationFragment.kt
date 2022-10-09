package com.hackyeon.eye_tracker.ui.calibration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hackyeon.eye_tracker.databinding.CalibrationFragmentBinding
import com.hackyeon.eye_tracker.ui.BaseFragment

class CalibrationFragment: BaseFragment() {
    private lateinit var binding: CalibrationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = CalibrationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


}