package com.example.gestionproduccionesnavegacion.ui.listaProducciones

import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import com.example.gestionproduccionesnavegacion.ui.Common.UiEvent

data class TodasProduccionesState(
    val producciones : List<Produccion> = emptyList(),
    val uiEvent: UiEvent? = null,
)
