package com.hackyeon.eye_tracker

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.concurrent.futures.await
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.hackyeon.eye_tracker.databinding.ActivityMainBinding
import com.hackyeon.eye_tracker.util.getAspectRatio
import com.hackyeon.eye_tracker.util.getAspectRatioString
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var PERMISSIONS_REQUIRED = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        checkPermission()
        setDeferred()
        bindCapture()
    }
    private fun setDeferred() {
        viewModel.enumerationDeferred = lifecycleScope.async {
            whenCreated {
                val provider = ProcessCameraProvider.getInstance(this@MainActivity).await()
                provider.unbindAll()
                try {
                    if(provider.hasCamera(viewModel.cameraSelector)) {
                        val camera = provider.bindToLifecycle(this@MainActivity, viewModel.cameraSelector)
                        QualitySelector
                            .getSupportedQualities(camera.cameraInfo)
                            .filter { quality ->
                                listOf(Quality.UHD, Quality.FHD, Quality.HD, Quality.SD)
                                    .contains(quality)
                            }.also {
                                viewModel.cameraQuality = it
                            }
                    } else {
                        // todo 전면 카메라를 지원하지 않는 경우
                    }
                } catch (e: Exception) {
                    // todo 전면 카메라를 지원하지 않는 경우
                }
            }
        }
    }

    private fun bindCapture() = lifecycleScope.launch {
        viewModel.enumerationDeferred?.let {
            it.await()
            viewModel.enumerationDeferred = null
        }
        val cameraProvider = ProcessCameraProvider.getInstance(this@MainActivity).await()
        viewModel.cameraSelector
        val quality = viewModel.cameraQuality?.get(0) ?: return@launch
        val qualitySelector = QualitySelector.from(quality)

        binding.preview.updateLayoutParams<ConstraintLayout.LayoutParams> {
            val orientation = this@MainActivity.resources.configuration.orientation
            dimensionRatio = quality.getAspectRatioString(quality,
                (orientation == Configuration.ORIENTATION_PORTRAIT))
        }

        val preview = Preview.Builder()
            .setTargetAspectRatio(quality.getAspectRatio(quality))
            .build()
            .apply {
                setSurfaceProvider(binding.preview.surfaceProvider)
            }

        val recorder = Recorder.Builder()
            .setQualitySelector(qualitySelector)
            .build()

        viewModel.videoCapture = VideoCapture.withOutput(recorder)

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                this@MainActivity,
                viewModel.cameraSelector,
                viewModel.videoCapture,
                preview
            )

        } catch (e: Exception) {

        }

    }


    private fun checkPermission() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            val permissionList = PERMISSIONS_REQUIRED.toMutableList()
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            PERMISSIONS_REQUIRED = permissionList.toTypedArray()
        }

        if(hasPermissions()) {
            // todo start camera
        } else {
            permissionLauncher.launch(PERMISSIONS_REQUIRED)
        }
    }

    private fun hasPermissions(): Boolean = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in PERMISSIONS_REQUIRED && !it.value)
                    permissionGranted = false
            }
            if(permissionGranted) {
                // todo start camera
            } else {
                Toast.makeText(this, "권한이 없으면 앱을 사용할수 없음", Toast.LENGTH_LONG).show()
                finish()
            }
        }

}