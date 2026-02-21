package com.example.navegacioncifradopsp.data.repository

import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.model.authentication.Auth
import com.example.navegacioncifradopsp.data.model.usuariosModel.UsuarioResponseItem
import com.example.navegacioncifradopsp.data.remote.apiService.AuthApiService
import com.example.navegacioncifradopsp.data.remote.utils.TokenManager
import com.example.navegacioncifradopsp.domain.model.Usuario
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApi: AuthApiService, private val tokenManager: TokenManager) {

    suspend fun login(username:String, password: String): NetworkResult<Unit>{
        try{
            val response = authApi.login(Auth(username, password))
            if(response.isSuccessful && response.body() != null){
                tokenManager.saveToken(response.body()?.token ?: "", response.body()?.refreshToken ?: "")
                return NetworkResult.Success(Unit)
            }else{
                return NetworkResult.Error(response.errorBody()?.string() ?: "Login failed")
            }
        }catch(e: Exception){
            return NetworkResult.Error(e.localizedMessage ?: "An error occurred")
        }

    }

    suspend fun register(usuario: Usuario): NetworkResult<Usuario>{
        try{
            val response = authApi.register(UsuarioResponseItem(usuario.email, usuario.nombre, usuario.usuario, usuario.password))

        }catch (e:Exception){

        }
    }
}