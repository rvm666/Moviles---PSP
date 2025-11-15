package com.example.gestionproduccionesnavegacion.ui.pantallasProducciones.infoProduccion

import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent

data class InfoProduccionState(
    val produccion: Produccion = Produccion(),
    val isEnable: Boolean = true,
    val uiEvent: UiEvent? = null,
)
