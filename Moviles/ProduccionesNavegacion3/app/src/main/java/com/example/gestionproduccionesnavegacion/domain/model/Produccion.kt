package com.example.gestionproduccionesnavegacion.domain.model

import java.time.LocalDate

data class Produccion(
    val id: Int = 0,
    val vista: Boolean? = null,
    var esPelicula: Boolean? = null,
    val nombre: String = "",
    val director: String = "",
    val numeroSeason: Int? = 0,
    val fechaLanzamiento: LocalDate? = null,
    val genero: String = "",
    val pais: String = "",
    val valoracion: Double = 0.0
)