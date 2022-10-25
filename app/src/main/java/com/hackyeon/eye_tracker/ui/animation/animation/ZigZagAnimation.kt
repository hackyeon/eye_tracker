package com.hackyeon.eye_tracker.ui.animation.animation

import android.animation.ValueAnimator
import android.view.View
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart

class ZigZagAnimation(
    private val container: View,
    icon: ImageView,
    private val speed: Int,
    private val callback: () -> Unit
): BaseAnimation(container, icon, speed) {
    /**
     * 가로이동 시작지점
     */
    private var startValues = arrayOf(0f, 0.25f, 0.50f, 0.75f)
    /**
     * 가로이동 끝 지점
     */
    private var endValues = arrayOf(0.25f, 0.50f, 0.75f, 1f)
    /**
     * 가로이동 현재 시작/끝 값 인덱스
     */
    private var currentIndex = 0

    // override
    override fun start() = bToT().start()
    override fun lToR(): ValueAnimator = super.lToR().apply {
        setFloatValues(startValues[currentIndex], endValues[currentIndex])
        onEndNext {
            currentIndex++
            if(currentIndex % 2 == 0) bToT().start()
            else tToB().start()
        }
        duration = (container.measuredWidth.toLong() / startValues.size) * speed
    }
    override fun tToB(): ValueAnimator = super.tToB().apply {
        onEndNext { lToR().start() }
    }
    override fun bToT(): ValueAnimator = super.bToT().apply {
        onEndNext {
            if(currentIndex < startValues.size) lToR().start()
            else callback()
        }
    }

}