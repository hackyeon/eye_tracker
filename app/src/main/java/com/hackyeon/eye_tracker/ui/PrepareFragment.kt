package com.hackyeon.eye_tracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.QualitySelector
import androidx.concurrent.futures.await
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.databinding.PrepareFragmentBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class PrepareFragment: Fragment() {
    private lateinit var binding: PrepareFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()

    private var enumerationDeferred: Deferred<Unit>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.prepare_fragment, container, false)
        return binding.root

//        startCamera()
    }

    init {
        enumerationDeferred = lifecycleScope.async {
            whenCreated {
                val provider = ProcessCameraProvider.getInstance(requireContext()).await()

                provider.unbindAll()
                try {
                    if(provider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)) {
                        val camera = provider.bindToLifecycle(requireActivity(), CameraSelector.DEFAULT_FRONT_CAMERA)
                        QualitySelector
                            .getSupportedQualities(camera.cameraInfo)
                            .also {
                                it.forEach { qu ->
                                    Log.d("aabb", "qu: $qu")
                                }
                            }
                    } else {

                    }


                } catch (e: Exception) {

                }

            }
        }
    }
}