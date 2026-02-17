package com.example.navegacioncifradopsp.ui.listaProducciones

import com.example.navegacioncifradopsp.domain.model.Produccion

data class ListaProduccionesState(
    val producciones: List<Produccion> = emptyList(),
)