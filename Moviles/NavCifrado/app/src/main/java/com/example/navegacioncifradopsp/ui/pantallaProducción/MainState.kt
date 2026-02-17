package com.example.navegacioncifradopsp.ui.pantallaProducción

import com.example.navegacioncifradopsp.domain.model.Produccion

data class MainState(
    val produccion: Produccion = Produccion(),
)