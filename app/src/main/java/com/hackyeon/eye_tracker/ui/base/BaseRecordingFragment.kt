package com.hackyeon.eye_tracker.ui.base

import android.os.Bundle
import com.hackyeon.eye_tracker.camera.CameraConnection
import com.hackyeon.eye_tracker.camera.CameraListener

abstract class BaseRecordingFragment: BaseFragment() {
    protected abstract val cameraListener: CameraListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CameraConnection.setListener(cameraListener)
    }

    override fun onDestroy() {
        CameraConnection.removeListener()
        super.onDestroy()
    }
}