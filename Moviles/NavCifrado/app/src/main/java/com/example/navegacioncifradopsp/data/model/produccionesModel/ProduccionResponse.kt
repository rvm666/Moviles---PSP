package com.example.navegacioncifradopsp.data.model.produccionesModel

import com.example.navegacioncifradopsp.common.Constantes
import com.example.navegacioncifradopsp.domain.model.Produccion
import com.google.gson.annotations.SerializedName

data class ProduccionResponse(
    @SerializedName(Constantes.GENRE_IDS)
    val genero: String,
    @SerializedName(Constantes.ORIGINAL_TITLE)
    val nombre: String,
    @SerializedName(Constantes.RELEASE_DATE)
    val fecha: Int,
    @SerializedName(Constantes.DIRECTOR)
    val director: String,
)


fun ProduccionResponse.toProduccion(): Produccion{
    return Produccion(
        nombre = this.nombre,
        director = director,
        lanzamiento = this.fecha,
        genero = this.genero,
    )
}



