package com.example.gestionproduccionesnavegacion.ui.pantallasUsuarios.editarUsuario

import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent

data class EditarUsuarioState(
    val uiEvent: UiEvent? = null,
    val usuario: Usuario = Usuario(),
    val vistas: Int = 0,
    val pendientes: Int = 0
)
