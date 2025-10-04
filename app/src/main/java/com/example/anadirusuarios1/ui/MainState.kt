package com.example.anadirusuarios1.ui

import com.example.anadirusuarios1.domain.model.Produccion
import java.time.LocalDate

data class MainState(
    val esPelicula: Boolean? = null,
    val nombre: String = "",
    val director: String = "",
    val numeroSeason: Int? = null,
    val fechaLanzamiento: LocalDate? = null,
    val genero: String = "",
    val pais: String = "",
    val valoracion: Double = 0.0,
    val produccion: Produccion = Produccion()

    )
