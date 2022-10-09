package com.hackyeon.eye_tracker.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.ui.customview.animation.AnimationController

class AnimationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var icon: ImageView
    private lateinit var controller: AnimationController

    init {
        initLayout()
    }

    private fun initLayout() {
        inflate(context, R.layout.animation_view, this)
        icon = this.findViewById(R.id.iv_icon)

//        this.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
//            override fun onGlobalLayout() {
//                controller = AnimationController(
//                    this@AnimationView.measuredWidth,
//                    this@AnimationView.measuredHeight,
//                    this@AnimationView.icon
//                )
//                this@AnimationView.viewTreeObserver.removeOnGlobalLayoutListener(this)
//            }
//        })
    }

    fun start() {
//        controller.start()
    }


}