package com.example.gestionproduccionesnavegacion.ui.AniadirProduccion

import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import com.example.gestionproduccionesnavegacion.ui.Common.UiEvent

data class AniadirProduccionState(
    val indiceProduccion: Int = 0,
    val produccion: Produccion = Produccion(),
    val isEnable: Boolean = true,
    val uiEvent: UiEvent? = null,
)
