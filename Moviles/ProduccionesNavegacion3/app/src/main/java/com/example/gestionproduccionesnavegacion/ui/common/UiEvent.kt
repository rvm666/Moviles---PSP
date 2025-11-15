package com.example.gestionproduccionesnavegacion.ui.common

interface UiEvent {
    data class Navigate(val route: String): UiEvent

    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent

}