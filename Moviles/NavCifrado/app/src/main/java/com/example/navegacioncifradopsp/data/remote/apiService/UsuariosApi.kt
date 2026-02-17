package com.example.navegacioncifradopsp.data.remote.apiService

import com.example.navegacioncifradopsp.data.model.usuariosModel.UsuarioResponse
import com.example.navegacioncifradopsp.data.model.usuariosModel.UsuarioResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuariosApi {

    @GET("users")
    suspend fun getAllUsuarios(
    ): Response<UsuarioResponse>


    @GET("users/{id}")
    suspend fun getById(
        @Path("id") id: Int
    ): Response<UsuarioResponseItem>


    @DELETE("users/{id}")
    suspend fun deleteUusario(
        @Path("id") id: Int
    ): Response<Unit>

    @POST("users")
    suspend fun guardarUsuario(
        @Body user: UsuarioResponseItem
    ): Response<UsuarioResponseItem>


    @PUT(("users/{id}"))
    suspend fun actualizarUsuario(
        @Path("id") id: Int,
        @Body user: UsuarioResponseItem
    ): Response<UsuarioResponseItem>

}