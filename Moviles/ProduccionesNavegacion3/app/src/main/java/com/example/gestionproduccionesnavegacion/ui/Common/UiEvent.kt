package com.example.gestionproduccionesnavegacion.ui.Common

interface UiEvent {
    object PopBackstack: UiEvent

    data class Navigate(val route: String): UiEvent

    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent
}