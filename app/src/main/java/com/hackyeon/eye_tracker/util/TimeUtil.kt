package com.hackyeon.eye_tracker.util

import android.util.Log
import java.util.concurrent.TimeUnit


object TimeUtil {
    private const val NANO_TO_SEC = 1000000000
    private const val NANO_TO_MS = 1000000

    /**
     * nano second -> string(00:00:00.00)
     * @param nanoSec nano second
     */
    fun calculateTime(nanoSec: Long): String {
        var calTime = "00:00:00.00"

        val time = nanoSec / NANO_TO_MS

        if (time > 0) {
            val hour = time / 60 / 60 / 1000
            calTime = if (hour < 10) "0$hour:" else "$hour:"
            val min = ((time / 1000) % 3600) / 60
            calTime += if (min < 10) "0$min:" else "$min:"
            val sec = (time / 1000) % 60
            calTime += if (sec < 10) "0$sec." else "$sec."
            val ms = (time % 1000) / 10
            calTime += if (ms < 10) "0$ms" else "$ms"
        }
        return calTime
    }
}