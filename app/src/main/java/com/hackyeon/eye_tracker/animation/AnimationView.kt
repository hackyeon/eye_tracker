package com.hackyeon.eye_tracker.animation

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import com.hackyeon.eye_tracker.R

class AnimationView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private lateinit var icon: ImageView


    init {
        initLayout()
    }

    private fun initLayout() {
        inflate(context, R.layout.animation_view, this)
        icon = this.findViewById(R.id.iv_icon)

        this.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
            override fun onGlobalLayout() {

                this@AnimationView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

    }




}