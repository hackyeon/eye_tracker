package com.hackyeon.eye_tracker.ui.animation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.ui.animation.animation.AnimationController
import com.hackyeon.eye_tracker.ui.base.BaseConfig

class AnimationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    // icon
    private lateinit var icon: ImageView
    // animation controller
    private lateinit var controller: AnimationController
    // animation callback
    private var mListener: AnimationListener? = null
    fun setAnimationListener(listener: AnimationListener) {
        this.mListener = listener
    }

    init {
        initLayout()
    }

    private fun initLayout() {
        inflate(context, R.layout.animation_view, this)
        icon = this.findViewById(R.id.iv_icon)

        this.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {
                setIcon()
                setController()
                mListener?.onReady()
                this@AnimationView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

    }

    private fun setIcon() {
        icon.layoutParams.width = measuredWidth / BaseConfig.HORIZONTAL_COUNT
        icon.layoutParams.height = measuredHeight / BaseConfig.VERTICAL_COUNT
        icon.visibility = View.GONE
    }

    private fun setController() {
        controller = AnimationController(this, icon) {
            mListener?.onAnimationFinished()
        }
    }

    fun startAnimation() = controller.start()

}