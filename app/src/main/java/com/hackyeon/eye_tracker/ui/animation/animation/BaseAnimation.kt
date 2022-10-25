package com.hackyeon.eye_tracker.ui.animation.animation

import android.animation.ValueAnimator
import android.renderscript.Sampler.Value
import android.view.View
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import com.hackyeon.eye_tracker.ui.animation.AnimationConfig
import kotlinx.coroutines.*
import kotlin.math.hypot

abstract class BaseAnimation(
    private val container: View,
    private val icon: ImageView,
    private val speed: Int
) {
    // scope
    private val animationJob = Job()
    private val animationScope = CoroutineScope(Dispatchers.Main + animationJob)
    /**
     * 애니메이션 종료 후 다음 애니메이션 실행
     */
    protected fun ValueAnimator.onEndNext(callback: () -> Unit) {
        doOnEnd {
            animationScope.launch {
                delay(AnimationConfig.ANIMATION_DELAY)
                callback()
            }
        }
    }
    /**
     * 애니메이션 시작
     */
    abstract fun start()

    /**
     * 가로이동 애니메이션
     */
    private val horizontalAnimation = ValueAnimator.AnimatorUpdateListener {
        val value = it.animatedValue as Float?
        value?.let { v ->
            icon.x = v * (container.measuredWidth - icon.measuredWidth)
        }
    }
    /**
     * 세로이동 애니메이션
     */
    private val verticalAnimation = ValueAnimator.AnimatorUpdateListener {
        val value = it.animatedValue as Float?
        value?.let { v ->
            icon.y = v * (container.measuredHeight - icon.measuredHeight)
        }
    }
    /**
     * 가로 및 아래로 이동 애니메이션
     */
    private val diagonalAnimation = ValueAnimator.AnimatorUpdateListener {
        val value = it.animatedValue as Float?
        value?.let { v ->
            icon.x = v * (container.measuredWidth - icon.measuredWidth)
            icon.y = it.animatedFraction * (container.measuredHeight - icon.measuredHeight)
        }
    }

    /**
     * 기본 애니메이션을 만든다
     */
    private fun createDefaultAnimation(): ValueAnimator = ValueAnimator.ofFloat().apply {
        interpolator = null
    }
    /**
     * 가로 및 아래로 이동
     */
    private fun createDiagonalAnimation(): ValueAnimator = createDefaultAnimation().apply {
        addUpdateListener(diagonalAnimation)
        duration = (hypot(container.measuredWidth.toFloat(), container.measuredHeight.toFloat()) * speed).toLong()
    }
    /**
     * 가로 이동
     */
    private fun createHorizontalAnimation(): ValueAnimator = createDefaultAnimation().apply {
        addUpdateListener(horizontalAnimation)
        duration = (container.measuredWidth * speed).toLong()
    }
    /**
     * 세로 이동
     */
    private fun createVerticalAnimation(): ValueAnimator = createDefaultAnimation().apply {
        addUpdateListener(verticalAnimation)
        duration = (container.measuredHeight * speed).toLong()
    }

    /**
     * left to right
     */
    protected open fun lToR(): ValueAnimator = createHorizontalAnimation().apply { setFloatValues(0f, 1f) }
    /**
     * right to left
     */
    protected open fun rToL(): ValueAnimator = createHorizontalAnimation().apply { setFloatValues(1f, 0f) }
    /**
     * top to bottom
     */
    protected open fun tToB(): ValueAnimator = createVerticalAnimation().apply { setFloatValues(0f, 1f) }
    /**
     * bottom to top
     */
    protected open fun bToT(): ValueAnimator = createVerticalAnimation().apply { setFloatValues(1f, 0f) }
    /**
     * left/top to right/bottom
     */
    protected open fun ltToRb(): ValueAnimator = createDiagonalAnimation().apply { setFloatValues(0f, 1f) }
    /**
     * right/top to left/bottom
     */
    protected open fun rtToLb(): ValueAnimator = createDiagonalAnimation().apply { setFloatValues(1f, 0f) }

}