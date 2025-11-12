package com.example.anadirusuarios1.domain.model

import org.threeten.bp.LocalDate

data class Produccion(var esPelicula: Boolean? = null, val nombre: String = "Nombre", val director: String = "Director", val numeroSeason: Int? = null, val fechaLanzamiento: LocalDate? = null, val genero: String = "", val pais: String = "", val valoracion: Double = 0.0)