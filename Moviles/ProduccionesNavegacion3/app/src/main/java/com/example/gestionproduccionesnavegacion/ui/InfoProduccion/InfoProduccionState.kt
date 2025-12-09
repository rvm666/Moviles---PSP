package com.example.gestionproduccionesnavegacion.ui.InfoProduccion

import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import com.example.gestionproduccionesnavegacion.ui.Common.UiEvent

data class InfoProduccionState(
    val produccion: Produccion = Produccion(),
    val isEnable: Boolean = true,
    val uiEvent: UiEvent? = null,
)
