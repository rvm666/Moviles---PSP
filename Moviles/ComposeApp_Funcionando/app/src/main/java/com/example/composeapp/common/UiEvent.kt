package com.example.composeapp.common

interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
}