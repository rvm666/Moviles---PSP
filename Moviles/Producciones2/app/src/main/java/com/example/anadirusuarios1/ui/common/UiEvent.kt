package com.example.anadirusuarios1.ui.common

sealed interface UiEvent {

    object PopBackstack: UiEvent

    data class Navigate(val route: String): UiEvent

    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent
}