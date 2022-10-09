package com.hackyeon.eye_tracker.ui.animation

import android.content.ContentValues
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.VideoRecordEvent
import androidx.core.content.ContextCompat
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.databinding.AnimationFragmentBinding
import com.hackyeon.eye_tracker.ui.BaseFragment
import com.hackyeon.eye_tracker.ui.customview.animation.AnimationController
import java.text.SimpleDateFormat
import java.util.*

class AnimationFragment: BaseFragment() {
    private lateinit var binding: AnimationFragmentBinding
    private var controller: AnimationController? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = AnimationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setController()

        binding.testButton.setOnClickListener {
            startRecording()
            controller?.start()
        }
    }

    /*
    *
    * callback
    * animation speed
    * flash speed
    *
    * */
    private fun setController() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                controller = AnimationController(
                    binding.flAnimationContainer.measuredWidth,
                    binding.flAnimationContainer.measuredHeight,
                    binding.ivIcon,
                    viewModel.animationSpeed.value?: MainViewModel.DEFAULT_ANIMATION_SPEED,
                    viewModel.flashTime.value?: MainViewModel.DEFAULT_FLASH_TIME,
                ) {
                    viewModel.stopRecording()
                    navigate(R.id.uploadFragment)
                }
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun startRecording() {
        if(viewModel.recordingState == null || viewModel.recordingState is VideoRecordEvent.Finalize) {

            val name = SimpleDateFormat("yyMMdd_HHmm", Locale.US)
                        .format(System.currentTimeMillis()) + ".mp4"
            val contentValues = ContentValues().apply {
                put(MediaStore.Video.Media.DISPLAY_NAME, name)
            }
            val mediaStoreOutput = MediaStoreOutputOptions.Builder(
                requireActivity().contentResolver,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                .setContentValues(contentValues)
                .build()

            // configure Recorder and Start recording to the mediaStoreOutput.
            viewModel.currentRecording = viewModel.videoCapture?.output
                ?.prepareRecording(requireActivity(), mediaStoreOutput)
                ?.start(ContextCompat.getMainExecutor(requireContext()), viewModel.captureListener)


        }

    }



}