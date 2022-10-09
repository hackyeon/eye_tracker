package com.hackyeon.eye_tracker.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.hackyeon.eye_tracker.MainViewModel

open class BaseFragment: Fragment() {
    protected val viewModel: MainViewModel by activityViewModels()

    protected fun navigate(@IdRes resId: Int) {
        findNavController().navigate(resId)
    }
}