package com.example.gestionproduccionesnavegacion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gestionproduccionesnavegacion.domain.model.Usuario

@Entity(
    tableName = "usuarios"
)

data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String = "",
    val contrasenia: String = "",
    val email: String = ""
)


fun Usuario.toUsuarioEntity() = UsuarioEntity(
    id = this.id,
    nombre = this.nombre,
    contrasenia = this.contrasenia,
    email = this.email
)


fun UsuarioEntity.toUsuario() = Usuario(
    id = this.id,
    nombre = this.nombre,
    contrasenia = this.contrasenia,
    email = this.email
)
