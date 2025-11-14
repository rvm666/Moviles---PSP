package com.example.gestionproduccionesnavegacion.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName="usuario_produccion_ref",
    primaryKeys = ["usuario", "produccion"],
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuario"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProduccionEntity::class,
            parentColumns = ["id"],
            childColumns = ["produccion"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["usuario"]),
        Index(value = ["produccion"])
    ]
)

data class UsuarioProduccionRef(
    val usuario: Int,
    val produccion: Int,
    val vista: Boolean = false,
)
