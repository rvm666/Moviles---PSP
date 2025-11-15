package com.example.gestionproduccionesnavegacion.data.repository

import com.example.gestionproduccionesnavegacion.data.local.dao.UsuariosDao
import com.example.gestionproduccionesnavegacion.data.local.entity.toUsuario
import com.example.gestionproduccionesnavegacion.data.local.entity.toUsuarioEntity
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import javax.inject.Inject

class RepositoryUsuarios @Inject constructor(private val usuariosDao: UsuariosDao){

    suspend fun getAllUsuarios(): List<Usuario>{
        return usuariosDao.getAllUsuarios().map { it.toUsuario() }
    }

    suspend fun getNumTotalUsuarios(): Int {
        return usuariosDao.getNumTotalUsuarios()
    }

    suspend fun insertarUsuario(usuario: Usuario): Boolean {
        val id = usuariosDao.insertUsuario(usuario.toUsuarioEntity())
        return id > 0
    }

    suspend fun getUsuarioById(id: Int): Usuario{
        val usuarioEntity = usuariosDao.getUsuarioById(id)
        return usuarioEntity.toUsuario()
    }

    suspend fun updateUsuario(usuario: Usuario): Boolean{
        val rows = usuariosDao.updateUsuario(usuario.toUsuarioEntity())
        return rows > 0
    }

    suspend fun deleteUsuario(id: Int): Boolean{
        val rows = usuariosDao.deleteUsuarioById(id)
        return rows > 0
    }
}