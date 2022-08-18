package com.hackyeon.eye_tracker.ui.customview.animation

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.widget.ImageView

class RectangleAnimation(private val width: Int, private val height: Int, private val icon: ImageView, private val speed: Int, private val callback: () -> Unit): Animator.AnimatorListener {
    enum class State{
        LTOR, RTOL, TTOB, BTOT;
    }
    private var state = State.LTOR

    fun start() {
        state = State.LTOR
        lToR.start()
    }

    private val widthAnimation = ValueAnimator.AnimatorUpdateListener {
        val value = it.animatedValue as Float?
        value?.let { v ->
            icon.x = v * (width - icon.measuredWidth)
        }
    }
    private val heightAnimation = ValueAnimator.AnimatorUpdateListener {
        val value = it.animatedValue as Float?
        value?.let { v ->
            icon.y = v * (height - icon.measuredHeight)
        }
    }

    private val lToR = ValueAnimator.ofFloat(0f, 1f).apply {
        addUpdateListener(widthAnimation)
        addListener(this@RectangleAnimation)
        duration = width.toLong() * speed
    }
    private val rToL = ValueAnimator.ofFloat(1f, 0f).apply {
        addUpdateListener(widthAnimation)
        addListener(this@RectangleAnimation)
        duration = width.toLong() * speed
    }
    private val tToB = ValueAnimator.ofFloat(0f, 1f).apply {
        addUpdateListener(heightAnimation)
        addListener(this@RectangleAnimation)
        duration = height.toLong() * speed
    }
    private val bToT = ValueAnimator.ofFloat(1f, 0f).apply {
        addUpdateListener(heightAnimation)
        addListener(this@RectangleAnimation)
        duration = height.toLong() * speed
    }



    override fun onAnimationStart(animation: Animator?) {
        icon.visibility = View.VISIBLE
    }
    override fun onAnimationEnd(animation: Animator?) {
        when(state) {
            State.LTOR -> {
                state = State.TTOB
                tToB.start()
            }
            State.TTOB -> {
                state = State.RTOL
                rToL.start()
            }
            State.RTOL -> {
                state = State.BTOT
                bToT.start()
            }
            State.BTOT -> {
                icon.visibility = View.GONE
                state = State.LTOR
                callback()
            }
        }
    }
    override fun onAnimationCancel(animation: Animator?) = Unit
    override fun onAnimationRepeat(animation: Animator?) = Unit
}