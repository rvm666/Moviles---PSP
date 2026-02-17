package com.example.navegacioncifradopsp.data.remote.apiService

import com.example.navegacioncifradopsp.data.model.produccionesModel.MovieResponse
import com.example.navegacioncifradopsp.data.model.produccionesModel.ProduccionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProduccionesApi {

    //No las pongo en constantes para saber que reciven y saber cual es la que estan usando

    @GET("search/movie")
    suspend fun buscarPorNombre(
        @Query("query") nombre: String,
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun buscarPorId(
        @Path("movie_id") movieId: Int
    ): Response<ProduccionResponse>

}