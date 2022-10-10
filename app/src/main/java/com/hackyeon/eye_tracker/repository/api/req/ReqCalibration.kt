package com.hackyeon.eye_tracker.repository.api.req

data class ReqCalibration (
    val name: String,
    val calibrationList: List<CalibrationData> = mutableListOf()
)

data class CalibrationData(
    val x: Int,
    val y: Int,
    val time: String
)
