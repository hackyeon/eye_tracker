package com.hackyeon.eye_tracker.ui.calibration

import com.hackyeon.eye_tracker.ui.calibration.data.CalibrationMode
import com.hackyeon.eye_tracker.ui.calibration.data.CoordinateItem

interface CalibrationListener {

    /**
     * calibration의 모드를 가져온다
     */
    fun getCalibrationMode(): CalibrationMode

    /**
     * View 사이즈 준비 완료
     */
    fun onReady()

    /**
     * calibration 아이콘의 위치 변경
     */
    fun onItemChanged(item: CoordinateItem)

    /**
     * calibration 종료
     */
    fun onCalibrationFinished()

    /**
     * calibration 간격을 가져온다
     */
    fun getCalibrationInterval(): Long?
}