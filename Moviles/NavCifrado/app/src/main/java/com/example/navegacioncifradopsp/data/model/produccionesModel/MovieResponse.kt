package com.example.navegacioncifradopsp.data.model.produccionesModel

import com.example.navegacioncifradopsp.common.Constantes
import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @SerializedName("peliculas")
    val peliculas: List<ProduccionResponse>
)