package com.example.navegacioncifradopsp.domain.usecase.usuariosUseCase

import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.model.usuariosModel.UsuarioResponseItem
import com.example.navegacioncifradopsp.data.repository.UsuariosRepository
import com.example.navegacioncifradopsp.domain.model.isValidUser
import javax.inject.Inject

class GuardarUsuarioUseCase @Inject constructor(private val repository: UsuariosRepository) {

    suspend operator fun invoke(user: UsuarioResponseItem): NetworkResult<Boolean> =
        isValidUser(user)
            .then { repository.guardarUsuario(user) }

}