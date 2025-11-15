package com.example.gestionproduccionesnavegacion.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import com.example.gestionproduccionesnavegacion.data.local.entity.UsuarioEntity


@Dao
interface UsuariosDao {

    @Query("SELECT * FROM usuarios")
    suspend fun getAllUsuarios(): List<UsuarioEntity>


    @Query("SELECT COUNT(*) FROM usuarios")
    suspend fun getNumTotalUsuarios(): Int


    @Query("DELETE FROM usuarios WHERE id = :id")
    suspend fun deleteUsuarioById(id: Int): Int

    @Query("SELECT * FROM usuarios WHERE id = :usuarioId")
    suspend fun getUsuarioById(usuarioId: Int): UsuarioEntity

    @Insert
    suspend fun insertUsuario(usuario: UsuarioEntity): Long

    @Update
    suspend fun updateUsuario(usuario: UsuarioEntity): Int

}