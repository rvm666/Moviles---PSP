package com.example.anadirusuarios1.domain.model

import org.threeten.bp.LocalDate

data class Produccion(
    var esPelicula: Boolean? = null,
    val nombre: String = "",
    val director: String = "",
    val numeroSeason: Int? = 0,
    val fechaLanzamiento: LocalDate? = null,
    val genero: String = "",
    val pais: String = "",
    val valoracion: Double = 0.0
)