package com.hackyeon.eye_tracker.ui.prepare

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.camera.CameraConnection
import com.hackyeon.eye_tracker.databinding.PrepareFragmentBinding
import com.hackyeon.eye_tracker.ui.base.BaseFragment
import com.hackyeon.eye_tracker.util.showConfirmAlert

class PrepareFragment: BaseFragment() {
    private lateinit var binding: PrepareFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = PrepareFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        regObserve()
        regListener()
    }

    private fun regObserve() {
        viewModel.nextRecording.observe(viewLifecycleOwner) {
            val isCalibration = it == MainViewModel.NextRecording.CALIBRATION
            binding.btnSettingAndClear.isSelected = !isCalibration
            binding.etFileName.isEnabled = isCalibration
            if(isCalibration) binding.etFileName.text = null
        }
    }

    private fun regListener() {
        binding.btnStart.setOnClickListener {
            val direction = if(viewModel.nextRecording.value == MainViewModel.NextRecording.CALIBRATION) PrepareFragmentDirections.toCalibration()
            else PrepareFragmentDirections.toAnimation()
            findNavController().navigate(direction)
        }
        binding.btnSettingAndClear.setOnClickListener {
            if(viewModel.nextRecording.value == MainViewModel.NextRecording.CALIBRATION) {
                findNavController().navigate(PrepareFragmentDirections.toSetting())
            } else {
                showConfirmAlert(R.string.clear_data_info) {
                    if(it) clearData()
                }
            }
        }
    }

}