package com.hackyeon.eye_tracker.ui.animation.animation

import android.view.View
import android.widget.ImageView

class AnimationController(
    container: View,
    icon: ImageView,
    speed: Int,
    callback: () -> Unit
) {
    fun start() = rectangleAnimation.start()

    private val rectangleAnimation = RectangleAnimation(container, icon, speed) {
        zigZagAnimation.start()
    }
    private val zigZagAnimation = ZigZagAnimation(container, icon, speed) {
        xAnimation.start()
    }
    private val xAnimation = XAnimation(container, icon, speed) {
        callback()
    }

}