package com.example.navegacioncifradopsp.common

interface UiEvent {

    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent

    data object NavigateBack : UiEvent
}