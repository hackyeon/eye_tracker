package com.hackyeon.eye_tracker.calibration

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.isVisible
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.calibration.data.CoordinateItem
import com.hackyeon.eye_tracker.util.HLog
import kotlinx.coroutines.*

class CalibrationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    // icon
    private lateinit var mainIcon: ImageView
    private lateinit var subIcon: ImageView

    // 좌표 리스트
    private val xList = mutableListOf<Int>()
    private val yList = mutableListOf<Int>()
    private val coordinateList = mutableListOf<CoordinateItem>()

    // scope
    private val calibrationJob = Job()
    private val calibrationScope = CoroutineScope(Dispatchers.Main + calibrationJob)

    /**
     * calibration callback
     */
    private var mListener: CalibrationListener? = null
    fun setCalibrationListener(listener: CalibrationListener) {
        this.mListener = listener
    }

    init {
        initLayout()
    }

    private fun initLayout() {
        inflate(context, R.layout.calibration_view, this)
        mainIcon = this.findViewById(R.id.iv_icon_main)
        subIcon = this.findViewById(R.id.iv_icon_sub)

        this.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                setIcon()
                setList()
                mListener?.onReady()
                this@CalibrationView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    /**
     * 캘리브레이션 시작
     */
    fun startCalibration() {
        calibrationScope.launch {
            coordinateList.forEachIndexed { index, item ->
                mListener?.onItemChanged(item)
                val iconToShow = if(mainIcon.isVisible) subIcon else mainIcon
                val iconToHide = if(mainIcon.isVisible) mainIcon else subIcon

                launch {
                    delay(CalibrationConfig.CALIBRATION_REMOVE_DELAY)
                    iconToHide.visibility = View.GONE
                }
                iconToShow.x = item.x.toFloat()
                iconToShow.y = item.y.toFloat()
                iconToShow.visibility = View.VISIBLE
                delay(CalibrationConfig.CALIBRATION_DELAY)

                // 마지막 인덱스인 경우
                if(index == coordinateList.lastIndex) {
                    delay(CalibrationConfig.CALIBRATION_REMOVE_DELAY)
                    iconToShow.visibility = View.GONE
                    mListener?.onCalibrationFinished()
                }
            }
        }
    }

    /**
     * 아이콘의 사이즈를 디바이스에 맞게 설정한다
     */
    private fun setIcon() {
        mainIcon.layoutParams.width = measuredWidth / CalibrationConfig.HORIZONTAL_COUNT
        mainIcon.layoutParams.height = measuredHeight / CalibrationConfig.VERTICAL_COUNT
        mainIcon.visibility = View.GONE

        subIcon.layoutParams.width = measuredWidth / CalibrationConfig.HORIZONTAL_COUNT
        subIcon.layoutParams.height = measuredHeight / CalibrationConfig.VERTICAL_COUNT
        subIcon.visibility = View.GONE
    }

    // 임시 true 45 false 23
    private var mode = false

    /**
     * calibration에 사용될 리스트를 만든다
     */
    private fun setList() {
        val xValue = measuredWidth / CalibrationConfig.HORIZONTAL_COUNT
        for (i in 0 until CalibrationConfig.HORIZONTAL_COUNT) {
            xList.add(xValue * i)
        }
        d("width: $measuredWidth")
        d("xList: $xList")
        d("xListSize: ${xList.size}")
        val yValue = measuredHeight / CalibrationConfig.VERTICAL_COUNT
        for (i in 0 until CalibrationConfig.VERTICAL_COUNT) {
            yList.add(yValue * i)
        }
        d("yListSize: ${yList.size}")

        xList.forEachIndexed { xIndex, x ->
            yList.forEachIndexed { yIndex, y ->
                if(mode) {
                    coordinateList.add(CoordinateItem(x, y))
                } else {
                    if(xIndex % 2 == 0 && yIndex % 2 == 0) {
                        coordinateList.add(CoordinateItem(x, y))
                    } else if(xIndex % 2 != 0 && yIndex % 2 != 0) {
                        coordinateList.add(CoordinateItem(x, y))
                    }
                }
            }
        }
        coordinateList.shuffle()
        d("list: $coordinateList")
        d("size: ${coordinateList.size}")

        coordinateList.add(0, CoordinateItem(xList[CalibrationConfig.HORIZONTAL_COUNT / 2], yList[0]))
        coordinateList.add(0, CoordinateItem(xList[CalibrationConfig.HORIZONTAL_COUNT / 2], yList[CalibrationConfig.VERTICAL_COUNT / 2]))

        d("list: $coordinateList")
        d("size: ${coordinateList.size}")

    }



    private fun d(msg: Any?) {
        HLog.d("## [${this.javaClass.simpleName}] ## $msg")
    }
}