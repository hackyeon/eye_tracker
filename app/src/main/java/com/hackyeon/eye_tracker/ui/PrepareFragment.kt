package com.hackyeon.eye_tracker.ui

import android.content.res.Configuration
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.concurrent.futures.await
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.navigation.Navigation
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.databinding.PrepareFragmentBinding
import com.hackyeon.eye_tracker.util.getAspectRatio
import com.hackyeon.eye_tracker.util.getAspectRatioString
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(
                PrepareFragmentDirections.actionPrepareFragmentToTestFragment()
            )
        }

    }

}