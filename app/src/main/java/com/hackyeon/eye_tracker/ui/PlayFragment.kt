package com.hackyeon.eye_tracker.ui

import android.content.ContentValues
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.VideoRecordEvent
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.databinding.PlayFragmentBinding
import java.util.*

class PlayFragment: Fragment() {
    private lateinit var binding: PlayFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()

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

        binding.startButton.setOnClickListener {
            val name = "CameraX-recording.mp4"
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
                ?.start(ContextCompat.getMainExecutor(requireContext()), captureListener)
        }

        binding.stopButton.setOnClickListener {
            if (viewModel.currentRecording == null || viewModel.recordingState is VideoRecordEvent.Finalize) {
                return@setOnClickListener
            }

            if (viewModel.currentRecording != null) {
                viewModel.currentRecording?.stop()
                viewModel.currentRecording = null
            }
        }

    }





    private val captureListener = Consumer<VideoRecordEvent> { event ->
        // cache the recording state
        if (event !is VideoRecordEvent.Status)
            viewModel.recordingState = event


//        if (event is VideoRecordEvent.Finalize) {
//            // display the captured video
//            lifecycleScope.launch {
//                navController.navigate(
//                    CaptureFragmentDirections.actionCaptureToVideoViewer(
//                        event.outputResults.outputUri
//                    )
//                )
//            }
//        }
    }

}