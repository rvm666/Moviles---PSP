package com.example.gestionproduccionesnavegacion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gestionproduccionesnavegacion.data.local.dao.ProduccionesDao
import com.example.gestionproduccionesnavegacion.data.local.dao.UsuariosDao
import com.example.gestionproduccionesnavegacion.data.local.entity.ProduccionEntity
import com.example.gestionproduccionesnavegacion.data.local.entity.UsuarioEntity

@Database(
    entities = [ProduccionEntity::class, UsuarioEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun produccionDao(): ProduccionesDao
    abstract fun usuarioDao(): UsuariosDao
}