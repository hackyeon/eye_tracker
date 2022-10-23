package com.hackyeon.eye_tracker.ui.animation.animation

import android.animation.ValueAnimator
import android.view.View
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import kotlin.math.hypot

class XAnimation(
    container: View,
    icon: ImageView,
    private val callback: () -> Unit
): BaseAnimation(container, icon) {
    /**
     * 아이콘 좌우 위치 확인용
     */
    private var isRight = false

    // override
    override fun start() = ltToRb().start()
    override fun bToT(): ValueAnimator = super.bToT().apply {
        onEndNext {
            if(isRight) rtToLb().start()
            else callback()
        }
    }
    override fun ltToRb(): ValueAnimator = super.ltToRb().apply {
        onEndNext {
            isRight = true
            bToT().start()
        }
    }
    override fun rtToLb(): ValueAnimator = super.rtToLb().apply {
        onEndNext {
            isRight = false
            bToT().start()
        }
    }

}