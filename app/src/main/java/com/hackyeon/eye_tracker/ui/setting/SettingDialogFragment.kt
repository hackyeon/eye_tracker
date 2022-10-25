package com.hackyeon.eye_tracker.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.ui.calibration.data.CalibrationMode
import com.hackyeon.eye_tracker.databinding.SettingDialogFragmentBinding
import com.hackyeon.eye_tracker.ui.calibration.CalibrationConfig

class SettingDialogFragment: DialogFragment() {
    private lateinit var binding: SettingDialogFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindNumberPicker()
        regObserve()
        regListener()
    }

    private fun regObserve() {
        viewModel.calibrationMode.observe(viewLifecycleOwner) {
            binding.switchCalibration.isChecked = it == CalibrationMode.HALF
        }
        viewModel.calibrationInterval.observe(viewLifecycleOwner) {
            val value = ((it + 100) / 1000).toInt()
            if(binding.npCalibrationInterval.value != value) binding.npCalibrationInterval.value = value
        }
    }

    private fun regListener() {
        binding.switchCalibration.setOnCheckedChangeListener { _, isChecked -> viewModel.setCalibrationMode(isChecked) }
        binding.npCalibrationInterval.setOnValueChangedListener { _, _, newVal -> viewModel.setCalibrationInterval(newVal) }
    }

    private fun bindNumberPicker() {
        binding.npCalibrationInterval.maxValue = CalibrationConfig.CALIBRATION_DELAY_MAX
        binding.npCalibrationInterval.minValue = CalibrationConfig.CALIBRATION_DELAY_MIN

    }

    override fun onDestroyView() {
        viewModel.saveSetting()
        super.onDestroyView()
    }
}