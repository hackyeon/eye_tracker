package com.hackyeon.eye_tracker

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.hackyeon.eye_tracker.camera.CameraConfig
import com.hackyeon.eye_tracker.camera.CameraConnection
import com.hackyeon.eye_tracker.databinding.ActivityMainBinding
import com.hackyeon.eye_tracker.util.HLog
import com.hackyeon.eye_tracker.util.extension.setFullScreen
import com.hackyeon.eye_tracker.util.showAlert
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private fun d(msg: Any?) {
        HLog.d("## [${this.javaClass.simpleName}] ## $msg")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        d("onCreate")
        setFullScreen()
        checkPermission()
    }

    override fun onDestroy() {
        d("onDestroy")
//        CameraConnection.closeCamera(this)
        super.onDestroy()
    }

    /**
     * MainActivity에 카메라를 바인딩한다
     */
    private fun bindCamera() = lifecycleScope.launch {
        CameraConnection.bindCamera(this@MainActivity, binding.preview)
    }

    /**
     * 권한 확인
     * 권한이 있는경우 카메라를 바인딩한다
     */
    private fun checkPermission() {
        if(hasPermissions()) {
            bindCamera()
        } else {
            permissionLauncher.launch(CameraConfig.cameraPermission)
        }
    }

    /**
     * 카메라 관련 권한 유무를 확인한다
     */
    private fun hasPermissions(): Boolean = CameraConfig.cameraPermission.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 카메라 관련 권한 런처
     */
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in CameraConfig.cameraPermission && !it.value) permissionGranted = false
            }
            if(permissionGranted) {
                bindCamera()
            } else {
                showAlert(R.string.permission_info) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(
                        Uri.parse("package:$packageName")).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        }

}