package com.example.gestionproduccionesnavegacion.domain.useCase.Usuarios

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryUsuarios
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import jakarta.inject.Inject

class GetAllUsuariosUseCase @Inject constructor(private val repostiroy: RepositoryUsuarios){

    operator suspend fun invoke(): List<Usuario>{
        return repostiroy.getAllUsuarios()
    }
}