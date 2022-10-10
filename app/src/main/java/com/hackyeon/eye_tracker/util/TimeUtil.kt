package com.hackyeon.eye_tracker.util

import java.util.concurrent.TimeUnit


object TimeUtil {
    private const val NANO_TO_SEC = 1000000000
    private const val NANO_TO_MS = 1000000

    /**
     * nano second -> string
     *
     */
    fun calculateTime(nanoSec: Long): String {
        var calTime = "00:00:00"



        val time = nanoSec / NANO_TO_MS

        String.format("%02d:%02d:%02d.%02d",
            TimeUnit.MILLISECONDS.toHours(time),
            TimeUnit.MILLISECONDS.toMinutes(time),
            TimeUnit.MILLISECONDS.toSeconds(time),
            TimeUnit.MILLISECONDS.toMillis(time)
        )




//        if (time > 0) {
//            val hour = time / 60 / 60
//
//            calTime = if (hour < 10) "0$hour:" else "$hour:"
//            val min = (time % 3600) / 60
//            calTime += if (min < 10) "0$min:" else "$min:"
//            val sec = time % 60
//            calTime += if (sec < 10) "0$sec" else "$sec"
//        }
//        return calTime
    }
}