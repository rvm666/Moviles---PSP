package com.example.gestionproduccionesnavegacion.ui.pantallasUsuarios.crearUsuario

import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent

data class CrearUsuarioState(
    val uiEvent: UiEvent? = null,
    val usuario: Usuario = Usuario()

)
