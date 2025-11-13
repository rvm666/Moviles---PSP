package com.example.gestionproduccionesnavegacion.domain.useCase.Usuarios

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryUsuarios
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import javax.inject.Inject

class UpdateUsuario @Inject constructor(private val repostiroy: RepositoryUsuarios) {

    operator suspend fun invoke(usuario: Usuario){
        repostiroy.updateUsuario(usuario)
    }
}