package com.example.composeapp.ui

import com.example.composeapp.domain.model.Produccion

data class MainState(
    val indiceProduccion: Int = 0,
    val produccion: Produccion = Produccion(),
    val isEnable: Boolean = true
)
