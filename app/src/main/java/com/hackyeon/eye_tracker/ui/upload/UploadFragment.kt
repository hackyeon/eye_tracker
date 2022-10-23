package com.hackyeon.eye_tracker.ui.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hackyeon.eye_tracker.databinding.UploadFragmentBinding
import com.hackyeon.eye_tracker.ui.base.BaseFragment

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        regListener()
    }

    private fun regListener() {
        binding.btnClearAndHome.setOnClickListener {
            clearData()
            findNavController().navigate(UploadFragmentDirections.toPrepare())
        }
    }
}