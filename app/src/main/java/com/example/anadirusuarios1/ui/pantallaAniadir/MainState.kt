package com.example.anadirusuarios1.ui.pantallaAniadir

import com.example.anadirusuarios1.domain.model.Produccion

data class MainState(
    val indiceProduccion: Int = 0,
    val produccion: Produccion = Produccion(),
    val mensaje: String? = null,
    val isEnable: Boolean = true
    )
