package com.example.gestionproduccionesnavegacion.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.gestionproduccionesnavegacion.data.local.entity.UsuarioEntity
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import kotlinx.coroutines.flow.Flow


@Dao
interface UsuariosDao {

    @Query("SELECT * FROM usuarios")
    suspend fun getAllUsuarios(): List<UsuarioEntity>


}