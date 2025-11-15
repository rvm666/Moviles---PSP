package com.example.gestionproduccionesnavegacion.domain.useCase.usuarios

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryUsuarios
import javax.inject.Inject

class DeleteUsuario @Inject constructor(private val repostiroy: RepositoryUsuarios) {

    suspend operator fun invoke(usuarioId: Int): Boolean {
        return repostiroy.deleteUsuario(usuarioId)
    }
}