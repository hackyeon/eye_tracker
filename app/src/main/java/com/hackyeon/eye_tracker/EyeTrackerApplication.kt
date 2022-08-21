package com.hackyeon.eye_tracker

import android.app.Application
import android.content.res.Resources
import android.util.Log
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig

class EyeTrackerApplication: Application(), CameraXConfig.Provider {
    override fun getCameraXConfig(): CameraXConfig {
        return CameraXConfig.Builder.fromConfig(Camera2Config.defaultConfig())
            .setMinimumLoggingLevel(Log.ERROR).build()
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        instance = this
        res = resources
    }

    companion object {
        lateinit var instance: EyeTrackerApplication
        lateinit var res: Resources
    }
}