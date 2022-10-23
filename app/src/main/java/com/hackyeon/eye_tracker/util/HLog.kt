package com.hackyeon.eye_tracker.util

import android.util.Log

object HLog {

    private val DEBUG = false
    private val TAG = "HLog"

    fun d(msg: Any?) {
        if(DEBUG) Log.d(TAG, "$msg")
    }

    fun i(msg: Any?) {
        if(DEBUG) Log.i(TAG, "$msg")
    }

    fun e(msg: Any?) {
        if(DEBUG) Log.e(TAG, "$msg")
    }
}
