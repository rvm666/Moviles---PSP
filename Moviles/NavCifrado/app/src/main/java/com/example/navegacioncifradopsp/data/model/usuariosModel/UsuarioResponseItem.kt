package com.example.navegacioncifradopsp.data.model.usuariosModel


import com.example.navegacioncifradopsp.common.Constantes
import com.google.gson.annotations.SerializedName

data class UsuarioResponseItem(
    val email: String = "",
    val nombre: String = "",
    val username: String = "",
    val password: String = "",
    val esAdmin: Boolean = false
)