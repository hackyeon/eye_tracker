package com.hackyeon.eye_tracker.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.calibration.data.CalibrationMode
import com.hackyeon.eye_tracker.databinding.SettingDialogFragmentBinding

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

        regObserve()
        regListener()
    }

    private fun regObserve() {
        viewModel.calibrationMode.observe(viewLifecycleOwner) {
            binding.switchCalibration.isChecked = it == CalibrationMode.HALF
        }
    }

    private fun regListener() {
        binding.switchCalibration.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setCalibrationMode(isChecked)
        }
    }

    override fun onDestroyView() {
        viewModel.saveSetting()
        super.onDestroyView()
    }
}