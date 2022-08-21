package com.hackyeon.eye_tracker

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    class ToDirection(val directions: NavDirections): NavigationCommand()
}
