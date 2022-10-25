package com.hackyeon.eye_tracker.ui.animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hackyeon.eye_tracker.camera.CameraConnection
import com.hackyeon.eye_tracker.camera.CameraListener
import com.hackyeon.eye_tracker.databinding.AnimationFragmentBinding
import com.hackyeon.eye_tracker.ui.base.BaseFragment
import com.hackyeon.eye_tracker.ui.base.BaseRecordingFragment

class AnimationFragment: BaseRecordingFragment() {
    private lateinit var binding: AnimationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = AnimationFragmentBinding.inflate(inflater, container, false).apply {
            animationView.setAnimationListener(animationListener)
        }
        return binding.root
    }

    override val cameraListener = object: CameraListener {
        override fun onRecordingStart() {
            binding.animationView.startAnimation()
        }
    }

    private val animationListener = object: AnimationListener {
        override fun onReady() {
            CameraConnection.startRecording(requireActivity())
        }
        override fun onAnimationFinished() {
            CameraConnection.stopRecording()
            findNavController().navigate(AnimationFragmentDirections.toUpload())
        }
        override fun getAnimationSpeed(): Int? = viewModel.animationSpeed.value
    }

}