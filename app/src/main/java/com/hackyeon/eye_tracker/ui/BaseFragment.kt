package com.hackyeon.eye_tracker.ui

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.hackyeon.eye_tracker.MainViewModel
import com.hackyeon.eye_tracker.util.HLog

open class BaseFragment: Fragment() {
    protected val viewModel: MainViewModel by activityViewModels()
    private lateinit var backCallback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
    }

    override fun onDetach() {
        super.onDetach()
        backCallback.remove()
    }

    private fun d(msg: Any?) {
        HLog.d("## [${this.javaClass.simpleName}] $msg")
    }
}