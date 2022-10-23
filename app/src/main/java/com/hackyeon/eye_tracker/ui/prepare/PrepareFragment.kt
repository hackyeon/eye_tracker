package com.hackyeon.eye_tracker.ui.prepare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hackyeon.eye_tracker.databinding.PrepareFragmentBinding
import com.hackyeon.eye_tracker.ui.base.BaseFragment

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
            // todo state
            findNavController().navigate(PrepareFragmentDirections.toAnimation())
//            findNavController().navigate(PrepareFragmentDirections.toCalibration())
        }
        binding.btnSetting.setOnClickListener {
            findNavController().navigate(PrepareFragmentDirections.toSetting())
        }
    }

}