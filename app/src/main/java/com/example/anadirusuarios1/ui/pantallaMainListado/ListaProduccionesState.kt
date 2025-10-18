package com.example.anadirusuarios1.ui.pantallaMainListado

import com.example.anadirusuarios1.domain.model.Produccion

data class ListaProduccionesState(
    val producciones : List<Produccion> = emptyList(),
    val mensaje : String? = null
)
