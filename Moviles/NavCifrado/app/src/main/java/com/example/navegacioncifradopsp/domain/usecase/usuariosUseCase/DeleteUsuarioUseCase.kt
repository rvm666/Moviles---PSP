package com.example.navegacioncifradopsp.domain.usecase.usuariosUseCase

import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.repository.UsuariosRepository
import com.example.navegacioncifradopsp.domain.model.isValidIdUsuario
import javax.inject.Inject

class DeleteUsuarioUseCase @Inject constructor(private val repository: UsuariosRepository) {

    suspend operator fun invoke(usuarioId: Int): NetworkResult<Boolean> =
        isValidIdUsuario(usuarioId)
            .then { repository.deleteUsuario(usuarioId) }

}