package com.example.gestionproduccionesnavegacion.data.local.entity

import androidx.room.Entity
import com.example.gestionproduccionesnavegacion.domain.model.Usuario

@Entity(
    tableName="usuario_produccion_ref",
    primaryKeys = ["usuario", "produccion"]
)

data class UsuarioProduccionRef(
    val usuario: Int,
    val produccion: Int,
    val vista: Boolean = false,
)
