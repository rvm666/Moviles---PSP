package com.example.composeapp.domain.model


data class Produccion(
    var esPelicula: Boolean? = null,
    val nombre: String = "",
    val director: String = "",
    val lanzamiento: String? = null,
    val numeroTemporadas: Int? = null,
    val genero: String = "",
    val pais: String = "",
    val valoracion: Double = 0.0
)
