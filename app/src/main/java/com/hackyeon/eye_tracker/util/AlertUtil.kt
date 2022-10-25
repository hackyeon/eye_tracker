package com.hackyeon.eye_tracker.util

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.hackyeon.eye_tracker.R

fun Fragment.showAlert(msg: String, callback:(()->Unit)? = null) = activity?.showAlert(msg,callback)
fun View.showAlert(msg: String, callback:(()->Unit)? = null) = context.showAlert(msg,callback)
fun Context.showAlert(@StringRes resId: Int, callback:(()->Unit)? = null) = showAlert(getString(resId),callback)
fun Fragment.showAlert(@StringRes resId: Int, callback:(()->Unit)? = null) = activity?.showAlert(resId,callback)
fun View.showAlert(@StringRes resId: Int, callback:(()->Unit)? = null) = context.showAlert(resId,callback)

// 확인 alert (string)
fun Context.showAlert(msg: String, callback:(()->Unit)? = null){
    val style = R.style.eyetracker_alert
    AlertDialog.Builder(this, style)
        .setMessage(msg)
        .setPositiveButton(getString(R.string.ok)){ dialog, _ ->
            callback?.let { it() }
            dialog.dismiss()
        }
        .setCancelable(false)
        .show()
}



// 확인 취소 alert
fun Context.showConfirmAlert(msg: String, callback: ((Boolean) -> Unit)? = null) {
    val style = R.style.eyetracker_alert
    AlertDialog.Builder(this, style)
        .setMessage(msg)
        .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            callback?.let{ it(true) }
            dialog.dismiss()
        }
        .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            callback?.let { it(false) }
            dialog.dismiss()
        }
        .setOnCancelListener {
            callback?.let{ it(false) }
        }
        .show()
}

fun Fragment.showConfirmAlert(msg: String, callback: ((Boolean) -> Unit)? = null) = activity?.showConfirmAlert(msg, callback)
fun View.showConfirmAlert(msg: String, callback: ((Boolean) -> Unit)? = null) = context.showConfirmAlert(msg, callback)
fun Context.showConfirmAlert(@StringRes resId: Int, callback: ((Boolean) -> Unit)? = null) = showConfirmAlert(getString(resId), callback)
fun Fragment.showConfirmAlert(@StringRes resId: Int, callback: ((Boolean) -> Unit)? = null) = activity?.showConfirmAlert(resId, callback)
fun View.showConfirmAlert(@StringRes resId: Int, callback: ((Boolean) -> Unit)? = null) = context.showConfirmAlert(resId, callback)