package com.example.gestionproduccionesnavegacion.ui.CrearUsuario

import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import com.example.gestionproduccionesnavegacion.ui.Common.UiEvent

data class CrearUsuarioState(
    val uiEvent: UiEvent? = null,
    val usuario: Usuario = Usuario()

)
