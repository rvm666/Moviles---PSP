package com.example.navegacioncifradopsp.domain.usecase.usuariosUseCase

import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.model.usuariosModel.UsuarioResponseItem
import com.example.navegacioncifradopsp.data.repository.UsuariosRepository
import com.example.navegacioncifradopsp.domain.model.isValidIdUsuario
import javax.inject.Inject

class GetUsuarioByIdUseCase @Inject constructor(private val repository: UsuariosRepository) {

    suspend operator fun invoke(usuarioId: Int): NetworkResult<UsuarioResponseItem> =
        isValidIdUsuario(usuarioId)
            .then { repository.getUsuarioById(usuarioId) }


}