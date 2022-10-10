package com.hackyeon.eye_tracker.ui.prepare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.databinding.PrepareFragmentBinding
import com.hackyeon.eye_tracker.ui.BaseFragment

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
        regListener()
    }
    private fun regListener() {
        binding.btnStart.setOnClickListener {
            navigate(R.id.actionToCalibrationFragment)
        }
        binding.btnSetting.setOnClickListener {
            navigate(R.id.actionToSettingDialogFragment)
        }
    }

}