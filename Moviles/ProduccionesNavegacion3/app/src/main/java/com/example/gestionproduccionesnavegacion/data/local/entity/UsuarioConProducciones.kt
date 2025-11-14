package com.example.gestionproduccionesnavegacion.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UsuarioConProducciones(

    @Embedded val usuario: UsuarioEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = UsuarioProduccionRef::class,
            parentColumn = "usuario",
            entityColumn = "produccion"
        )
    )
    val producciones: List<ProduccionEntity>
)
