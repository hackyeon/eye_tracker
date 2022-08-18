package com.hackyeon.eye_tracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.databinding.UploadFragmentBinding

class UploadFragment: Fragment() {
    private lateinit var binding: UploadFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.upload_fragment, container, false)
        return binding.root
    }
}