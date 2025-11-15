package com.example.gestionproduccionesnavegacion.domain.useCase.usuarios

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryUsuarios
import javax.inject.Inject

class GetNumTotalUsuarios @Inject constructor(private val repostiroy: RepositoryUsuarios) {

    suspend operator fun invoke(): Int{
        return repostiroy.getNumTotalUsuarios()
    }
}