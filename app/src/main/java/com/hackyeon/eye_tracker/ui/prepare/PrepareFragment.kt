package com.hackyeon.eye_tracker.ui.prepare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.databinding.PrepareFragmentBinding

class PrepareFragment: Fragment() {
    private lateinit var binding: PrepareFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.prepare_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // tood 나중에 뷰모델로
        binding.btnStart.setOnClickListener {
            viewModel.navigate(PrepareFragmentDirections.actionPrepareFragmentToPlayFragment())
        }

        binding.btnSetting.setOnClickListener {
            viewModel.navigate(PrepareFragmentDirections.actionPrepareFragmentToSettingDialogFragment())
        }
    }

}