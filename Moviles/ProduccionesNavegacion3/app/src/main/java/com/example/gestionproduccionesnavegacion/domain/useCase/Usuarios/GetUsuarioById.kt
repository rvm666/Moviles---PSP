package com.example.gestionproduccionesnavegacion.domain.useCase.Usuarios

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryUsuarios
import javax.inject.Inject

class GetUsuarioById @Inject constructor(private val repostiroy: RepositoryUsuarios) {

    operator suspend fun invoke(id: Int) =
        repostiroy.getUsuarioById(id)

}