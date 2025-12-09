package com.example.gestionproduccionesnavegacion.data.repository

import com.example.gestionproduccionesnavegacion.data.local.dao.UsuariosDao
import com.example.gestionproduccionesnavegacion.data.local.entity.toUsuario
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import jakarta.inject.Inject

class RepositoryUsuarios @Inject constructor(private val usuariosDao: UsuariosDao){

    suspend fun getAllUsuarios(): List<Usuario>{
        return usuariosDao.getAllUsuarios().map { it.toUsuario() }
    }
}