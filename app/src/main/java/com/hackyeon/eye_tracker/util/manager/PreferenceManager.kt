package com.hackyeon.eye_tracker.util.manager

import android.content.Context
import android.content.SharedPreferences
import com.hackyeon.eye_tracker.EyeTrackerApplication

object PreferenceManager {
    private val preferences: SharedPreferences =
        EyeTrackerApplication.instance.getSharedPreferences(EyeTrackerApplication.instance.packageName, Context.MODE_PRIVATE)

    fun setString(key: String, value: String) = preferences.edit().putString(key, value).apply()
    fun getString(key: String, default: String): String = preferences.getString(key, default)?: default
    fun getString(key: String): String? = preferences.getString(key, null)

    fun setInt(key: String, value: Int) = preferences.edit().putInt(key, value).apply()
    fun getInt(key: String, default: Int): Int = preferences.getInt(key, default)
    fun getInt(key: String): Int? = if(!preferences.contains(key)) null else getInt(key, -1)

    fun setLong(key: String, value: Long) = preferences.edit().putLong(key, value).apply()
    fun getLong(key: String, default: Long): Long = preferences.getLong(key, default)
    fun getLong(key: String): Long? = if(!preferences.contains(key)) null else getLong(key, -1)

    fun setBoolean(key: String, value: Boolean) = preferences.edit().putBoolean(key, value).apply()
    fun getBoolean(key: String, default: Boolean): Boolean = preferences.getBoolean(key, default)
    fun getBoolean(key: String): Boolean? = if(!preferences.contains(key)) null else getBoolean(key, false)

    fun removeObject(key: String) = preferences.edit().remove(key).apply()

    fun clearAll() = preferences.edit().clear().apply()
}