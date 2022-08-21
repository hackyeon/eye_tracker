package com.hackyeon.eye_tracker.ui.play.settings

sealed class SettingType {
    class Switch(val frontText: String, val backText: String): SettingType()
    object Progress: SettingType()
    object Picker: SettingType()
    object Spinner: SettingType()
    object Popup: SettingType()
}
