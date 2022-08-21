package com.hackyeon.eye_tracker.vo

import android.content.Context
import androidx.annotation.StringRes
import com.hackyeon.eye_tracker.R
import com.hackyeon.eye_tracker.ui.play.settings.SettingType

data class SettingVO(
    @StringRes val id: Int,
    val type: SettingType
) {
    companion object {
        fun getCameraSetting(context: Context): MutableList<SettingVO> {
            return mutableListOf<SettingVO>().apply {
                add(SettingVO(R.string.camera_mode, SettingType.Switch(context.getString(R.string.front_camera), context.getString(R.string.back_camera))))
                add(SettingVO(R.string.screen_mode, SettingType.Switch(context.getString(R.string.portrait_mode), context.getString(R.string.landscape_mode))))
            }
        }

        fun getAnimationSetting(context: Context): MutableList<SettingVO> {
            return mutableListOf<SettingVO>().apply {
                add(SettingVO(R.string.animation_speed, SettingType.Picker))
                add(SettingVO(R.string.flash_time, SettingType.Picker))
            }
        }

    }
}
