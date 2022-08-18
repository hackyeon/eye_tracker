package com.hackyeon.eye_tracker.ui.customview.animation

import android.widget.ImageView

class AnimationController(
    private val width: Int,
    private val height: Int,
    private val icon: ImageView,
    private val animationSpeed: Int,
    private val flashTime: Int,
    private val endCallback: () -> Unit
) {


    private val rectangleAnimation = RectangleAnimation(width, height, icon, animationSpeed) { endCallback() }

    fun start() {
        rectangleAnimation.start()
    }

}