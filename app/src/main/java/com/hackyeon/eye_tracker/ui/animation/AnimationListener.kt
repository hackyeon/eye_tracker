package com.hackyeon.eye_tracker.ui.animation

interface AnimationListener {

    /**
     * View 사이즈 준비 완료
     */
    fun onReady()

    /**
     * 애니메이션 종료
     */
    fun onAnimationFinished()
}