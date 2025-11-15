package com.example.gestionproduccionesnavegacion.ui.pantallasProducciones.listaProducciones

import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent

data class TodasProduccionesState(
    val producciones : List<Produccion> = emptyList(),
    val uiEvent: UiEvent? = null,
)
