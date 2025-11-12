package com.example.gestionproduccionesnavegacion.ui.ListaUsuarios

import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import com.example.gestionproduccionesnavegacion.ui.Common.UiEvent

data class ListaUsuariosState(
    val usuarios: List<Usuario> = emptyList(),
    val uiEvent: UiEvent? = null,
)
