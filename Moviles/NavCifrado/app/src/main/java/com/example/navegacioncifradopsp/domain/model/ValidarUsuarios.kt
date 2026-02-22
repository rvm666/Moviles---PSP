package com.example.navegacioncifradopsp.domain.model

import com.example.navegacioncifradopsp.common.Constantes
import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.model.usuariosModel.UsuarioResponseItem

fun isValidIdUsuario(id: Int): NetworkResult<Boolean> {
    return  if (id < 0 || id > 10)  NetworkResult.Error(Constantes.ID_NEGATIVO) else  NetworkResult.Success(true)
}

fun isValidUser(user: UsuarioResponseItem): NetworkResult<Boolean> {
    return  if (user.nombre == "" || user.email == "" || user.username == "")  NetworkResult.Error(
        Constantes.CAMPOS_VACIOS) else  NetworkResult.Success(true)
}