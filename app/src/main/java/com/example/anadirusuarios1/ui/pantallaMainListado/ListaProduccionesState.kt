package com.example.anadirusuarios1.ui.pantallaMainListado

import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.ui.common.UiEvent

data class ListaProduccionesState(
    val producciones : List<Produccion> = emptyList(),
    val uiEvent: UiEvent? = null,
)
