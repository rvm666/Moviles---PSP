package com.example.anadirusuarios1.domain.model

import java.time.LocalDate

data class Produccion(val esSerie: Boolean? = null, val nombre: String = "", val director: String = "", val numeroSeason: Int? = null, val fechaLanzamiento: LocalDate? = null, val genero: String = "", val pais: String = "", val valoracion: Double = 0.0)