package com.example.navegacioncifradopsp.data.model.usuariosModel


import com.example.navegacioncifradopsp.common.Constantes
import com.google.gson.annotations.SerializedName

data class UsuarioResponseItem(
    @SerializedName(Constantes.EMAIL)
    val email: String = "",
    @SerializedName(Constantes.ID)
    val id: Int = 0,
    @SerializedName(Constantes.NAME)
    val name: String = "",
    @SerializedName(Constantes.USERNAME)
    val username: String = "",

)