package com.hackyeon.eye_tracker.ui.play

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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.databinding.PlayFragmentBinding
import com.hackyeon.eye_tracker.ui.customview.animation.AnimationController
import java.text.SimpleDateFormat
import java.util.*

class PlayFragment: Fragment() {
    private lateinit var binding: PlayFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var controller: AnimationController? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.play_fragment, container, false)
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
                    viewModel.navigate(PlayFragmentDirections.actionPlayFragmentToUploadFragment())
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