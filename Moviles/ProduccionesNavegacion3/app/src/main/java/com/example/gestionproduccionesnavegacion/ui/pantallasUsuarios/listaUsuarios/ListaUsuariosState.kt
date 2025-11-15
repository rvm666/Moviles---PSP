package com.example.gestionproduccionesnavegacion.ui.pantallasUsuarios.listaUsuarios

import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent

data class ListaUsuariosState(
    val usuarios: List<Usuario> = emptyList(),
    val uiEvent: UiEvent? = null,
)
