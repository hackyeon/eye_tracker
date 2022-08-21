package com.hackyeon.eye_tracker.ui.play.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.databinding.SettingDialogFragmentBinding

class SettingDialogFragment: DialogFragment() {
    private lateinit var binding: SettingDialogFragmentBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = SettingDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}