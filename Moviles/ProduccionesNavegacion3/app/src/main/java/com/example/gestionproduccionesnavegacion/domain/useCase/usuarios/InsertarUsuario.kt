package com.example.gestionproduccionesnavegacion.domain.useCase.usuarios

import com.example.gestionproduccionesnavegacion.data.repository.RepositoryUsuarios
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import javax.inject.Inject

class InsertarUsuario @Inject constructor(private val repostiroy: RepositoryUsuarios) {

    suspend operator fun invoke(usuario: Usuario): Boolean{
       return repostiroy.insertarUsuario(usuario)
    }
}