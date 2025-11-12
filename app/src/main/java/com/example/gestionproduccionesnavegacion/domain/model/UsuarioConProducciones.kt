package com.example.gestionproduccionesnavegacion.domain.model

data class UsuarioConProducciones(
    val usuario: Usuario,
    val produccionesVistas: List<Produccion>,
    val produccionesPendientes: List<Produccion>
)
