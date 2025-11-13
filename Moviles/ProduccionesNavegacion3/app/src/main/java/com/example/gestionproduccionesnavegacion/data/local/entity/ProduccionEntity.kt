package com.example.gestionproduccionesnavegacion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gestionproduccionesnavegacion.domain.model.Produccion
import java.time.LocalDate
import kotlin.toString

@Entity(
    tableName = "producciones"
)
data class ProduccionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val vista: Boolean? = null,
    var esPelicula: Boolean? = null,
    val nombre: String = "",
    val director: String = "",
    val numeroSeason: Int? = null,
    val fechaLanzamiento: String? = null,
    val genero: String = "",
    val pais: String = "",
    val valoracion: Double = 0.0
)


fun Produccion.toProduccionEntity() = ProduccionEntity(
    id = this.id,
    vista = this.vista,
    esPelicula = this.esPelicula,
    nombre = this.nombre,
    director = this.director,
    numeroSeason = this.numeroSeason,
    fechaLanzamiento = this.fechaLanzamiento?.toString(),
    genero = this.genero,
    pais = this.pais,
    valoracion = this.valoracion
)

fun ProduccionEntity.toProduccion() = Produccion(
    id = this.id,
    vista = this.vista,
    esPelicula = this.esPelicula,
    nombre = this.nombre,
    director = this.director,
    numeroSeason = this.numeroSeason,
    fechaLanzamiento = this.fechaLanzamiento?.let { LocalDate.parse(it) },
    genero = this.genero,
    pais = this.pais,
    valoracion = this.valoracion
)