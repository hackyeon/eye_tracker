package com.hackyeon.eye_tracker.ui.calibration.data

enum class CalibrationMode(val value: Int) {
    FULL(0), HALF(1);

    companion object {
        fun modeToInt(mode: CalibrationMode): Int {
            return when(mode) {
                FULL -> FULL.value
                HALF -> HALF.value
            }
        }

        fun intToMode(value: Int): CalibrationMode {
            return when(value) {
                FULL.value -> FULL
                HALF.value -> HALF
                else -> FULL
            }
        }
    }

}