package com.example.anadirusuarios1.ui

import com.example.anadirusuarios1.domain.model.Produccion
import java.time.LocalDate

data class MainState(
    val indiceProduccion: Int = 0,
    val produccion: Produccion = Produccion(),
    val mensaje: String? = null
    )
