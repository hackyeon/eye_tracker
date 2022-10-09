package com.hackyeon.eye_tracker.camera

import android.Manifest
import android.os.Build

object CameraConfig {
    /**
     * 카메라 관련 권한을 가져온다
     */
    val cameraPermission: Array<String>
        get() = if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
        else arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)




}