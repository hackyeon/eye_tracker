package com.hackyeon.eye_tracker

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Quality
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.concurrent.futures.await
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackyeon.eye_tracker.util.getNameString
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var PERMISSIONS_REQUIRED = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO)


    init {

//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
//            val permissionList = PERMISSIONS_REQUIRED.toMutableList()
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            PERMISSIONS_REQUIRED = permissionList.toTypedArray()
//        }
//        Log.d("aabb", "application: $application")
//
//
//        val t = PERMISSIONS_REQUIRED.all {
//            ContextCompat.checkSelfPermission(application.applicationContext, it) == PackageManager.PERMISSION_GRANTED
//        }


    }


    /**
     * for camera
     */
    var enumerationDeferred: Deferred<Unit>? = null
    val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    var cameraQuality: List<Quality>? = null

    var videoCapture: VideoCapture<Recorder>? = null

    fun initQuality() {
    }


}
