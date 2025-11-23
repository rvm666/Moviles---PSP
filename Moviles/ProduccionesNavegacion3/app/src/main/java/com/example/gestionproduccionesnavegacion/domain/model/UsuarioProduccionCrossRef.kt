package com.example.gestionproduccionesnavegacion.domain.model

data class UsuarioProduccionCrossRef(
    val usuario: Int,
    val produccion: Int,
    val vista: Boolean = false,
    val valoracion: Double? = null
)