package com.hackyeon.eye_tracker

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.concurrent.futures.await
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.hackyeon.eye_tracker.databinding.ActivityMainBinding
import com.hackyeon.eye_tracker.util.HLog
import com.hackyeon.eye_tracker.util.extension.getAspectRatio
import com.hackyeon.eye_tracker.util.extension.setFullScreen
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private fun d(msg: Any?) {
        HLog.d("## [${this.javaClass.simpleName}] ## $msg")
    }

    override fun onDestroy() {
        super.onDestroy()
        d("onDestroy")
    }

    override fun onResume() {
        super.onResume()
        d("onResume")
    }

    override fun onPause() {
        super.onPause()
        d("onPause")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        d("onCreate")
        setFullScreen()
        checkPermission()

        regObserve()
    }

    private fun regObserve() {
        viewModel.navigationCommand.observe(this) {
            when(it) {
                is NavigationCommand.ToDirection -> {
                    try {
                        binding.fragmentContainer.findNavController().navigate(it.directions)
                    } catch (e: Exception) {
                        d("error navigation $e")
                    }
                }
            }
        }

    }


    //////////////////////////////////////////////
    //////////////// for camera //////////////////
    //////////////////////////////////////////////
    /**
     * 카메라를 엑티비티에 바인딩한다
     */
    private fun bindCamera() = lifecycleScope.launch {
        viewModel.qualityList = getQuality()
        if(viewModel.qualityList.isEmpty()) {
            // 전면 카메라가 없거나 카메라 품질을 가져올수 없는 경우
            return@launch
        }

        val cameraProvider = ProcessCameraProvider.getInstance(this@MainActivity).await()
        val quality = viewModel.qualityList.first()
        val qualitySelector = QualitySelector.from(quality)

        val preview = Preview.Builder()
            .setTargetAspectRatio(quality.getAspectRatio(quality))
            .build()
            .apply { setSurfaceProvider(binding.preview.surfaceProvider) }

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
            // 전면 카메라가 없는경우
        }
    }

    /**
     * 카메라의 품질을 가져온다
     */
    private suspend fun getQuality(): List<Quality> {
        val provider = ProcessCameraProvider.getInstance(this@MainActivity).await()
        provider.unbindAll()
        return try {
            if(provider.hasCamera(viewModel.cameraSelector)) {
                val camera = provider.bindToLifecycle(this@MainActivity, viewModel.cameraSelector)
                QualitySelector.getSupportedQualities(camera.cameraInfo)
            } else {
                // 전면 카메라가 없는 경우
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    //////////////////////////////////////////////
    ////////////// for permission ////////////////
    //////////////////////////////////////////////
    // todo 권한 연속거절 관련
    /**
     * 권한 확인
     * 권한이 있는경우 카메라를 바인딩한다
     */
    private fun checkPermission() {
        if(hasPermissions()) {
            bindCamera()
        } else {
            permissionLauncher.launch(viewModel.getPermissionRequired())
        }
    }

    private fun hasPermissions(): Boolean = viewModel.getPermissionRequired().all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in viewModel.getPermissionRequired() && !it.value) permissionGranted = false
            }
            if(permissionGranted) {
                bindCamera()
            } else {
                Toast.makeText(this, "권한이 없으면 앱을 사용할수 없음", Toast.LENGTH_LONG).show()
            }
        }

}