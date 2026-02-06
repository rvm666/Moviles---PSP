package com.example.composeapp.ui.util


import androidx.window.core.layout.WindowSizeClass


enum class DeviceConfiguration {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    DESKTOP;

    companion object {
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            val widthClass = !windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
            val heightClass = windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)

            return when {
                widthClass && heightClass -> MOBILE_PORTRAIT
                widthClass && !heightClass -> MOBILE_LANDSCAPE
                else -> DESKTOP
            }
        }
    }
}