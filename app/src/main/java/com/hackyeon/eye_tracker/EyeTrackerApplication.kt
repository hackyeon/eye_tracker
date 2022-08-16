package com.hackyeon.eye_tracker

import android.app.Application
import android.content.res.Resources

class EyeTrackerApplication: Application() {

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