package com.example.navegacioncifradopsp.data.remote.apiService

import com.example.navegacioncifradopsp.data.model.authentication.Auth
import com.example.navegacioncifradopsp.data.model.authentication.LoginResponse
import com.example.navegacioncifradopsp.data.model.usuariosModel.UsuarioResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("usuarios/login")
    suspend fun login(
        @Body auth: Auth,
    ): Response<LoginResponse>

    @GET("usuarios/refreshToken")
    suspend fun refreshToken(
        @Header("Authorization") token: String,
    ): Response<LoginResponse>

    @POST("usuarios/registro")
    suspend fun register(
        @Body usuario: UsuarioResponseItem,
    ): Response<UsuarioResponseItem>
}
