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
import kotlin.random.Random

class CalibrationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    // icon
    private lateinit var mainIcon: ImageView
    private lateinit var subIcon: ImageView

    // 좌표 리스트
    private val xList = mutableListOf<Int>()
    private val yList = mutableListOf<Int>()
    private val coordinateList = mutableListOf<CoordinateItem>()

    // iconView handler
    private val mainHandler = Handler(Looper.getMainLooper())
    private val subHandler = Handler(Looper.getMainLooper())
    private val mainRunnable = Runnable {

    }
    private val subRunnable = Runnable {

    }

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
                startCalibration()
                this@CalibrationView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun startCalibration() {
        mListener?.onStartCalibration()


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