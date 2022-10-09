package com.hackyeon.eye_tracker.ui.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hackyeon.eye_tracker.databinding.UploadFragmentBinding
import com.hackyeon.eye_tracker.ui.BaseFragment

class UploadFragment: BaseFragment() {
    private lateinit var binding: UploadFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = UploadFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}