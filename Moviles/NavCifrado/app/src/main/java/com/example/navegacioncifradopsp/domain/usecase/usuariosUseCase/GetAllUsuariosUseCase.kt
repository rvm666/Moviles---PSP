package com.example.navegacioncifradopsp.domain.usecase.usuariosUseCase

import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.model.usuariosModel.UsuarioResponseItem
import com.example.navegacioncifradopsp.data.repository.UsuariosRepository
import javax.inject.Inject

class GetAllUsuariosUseCase @Inject constructor(private val repository: UsuariosRepository) {

    suspend operator fun invoke(): NetworkResult<List<UsuarioResponseItem>> =
        repository.getAllUsuarios()

}