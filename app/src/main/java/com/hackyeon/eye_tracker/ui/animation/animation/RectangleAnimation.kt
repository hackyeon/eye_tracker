package com.hackyeon.eye_tracker.ui.animation.animation

import android.animation.ValueAnimator
import android.view.View
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart

class RectangleAnimation(
    container: View,
    private val icon: ImageView,
    speed: Int,
    private val callback: () -> Unit
): BaseAnimation(container, icon, speed) {
    // override
    override fun start() = lToR().start()
    override fun lToR(): ValueAnimator = super.lToR().apply {
        doOnStart { icon.visibility = View.VISIBLE }
        onEndNext { tToB().start() }
    }
    override fun rToL(): ValueAnimator = super.rToL().apply {
        onEndNext { bToT().start() }
    }
    override fun tToB(): ValueAnimator = super.tToB().apply {
        onEndNext { rToL().start() }
    }
    override fun bToT(): ValueAnimator = super.bToT().apply {
        onEndNext { callback() }
    }
}