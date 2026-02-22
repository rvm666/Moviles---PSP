package com.example.navegacioncifradopsp.data.repository

import com.example.navegacioncifradopsp.common.Constantes
import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.model.ErrorResponse
import com.example.navegacioncifradopsp.data.model.authentication.Auth
import com.example.navegacioncifradopsp.data.model.authentication.LoginResponse
import com.example.navegacioncifradopsp.data.model.usuariosModel.UsuarioResponseItem
import com.example.navegacioncifradopsp.data.remote.apiService.AuthApiService
import com.example.navegacioncifradopsp.data.remote.utils.BaseApiResponse
import com.example.navegacioncifradopsp.data.remote.utils.TokenManager
import com.example.navegacioncifradopsp.domain.model.Usuario
import com.google.gson.Gson
import timber.log.Timber
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApiService,
    private val tokenManager: TokenManager,
    private val gson: Gson = Gson()
): BaseApiResponse() {

    fun parseErrorResponse(raw: String, gson: Gson = Gson()): ErrorResponse? {
        return try {
            gson.fromJson(raw, ErrorResponse::class.java)
        } catch (_: Exception) {
            null
        }
    }
    suspend fun login(username:String, password: String): NetworkResult<Unit>{
        try{
            val response: NetworkResult<LoginResponse> = safeApiCall{
                authApi.login(Auth(username, password))
            }
            return when (response){
                is NetworkResult.Success ->{
                    val token = response.data.token
                    val refresh = response.data.refreshToken

                    tokenManager.saveToken(token, refresh)
                    NetworkResult.Success(Unit)

                }
                is NetworkResult.Error -> {
                    val error = parseErrorResponse(response.message, gson)
                    when (error?.codigo){
                        "CUENTA_NO_ACTIVADA" ->{
                            NetworkResult.Error(error.mensaje ?: "Cuenta no activada, revisa email")
                        }
                        "CREDENCIALES_INVALIDAS" -> {
                            NetworkResult.Error("Usuario o contraseña incorrectos")
                        }
                        else -> {
                            NetworkResult.Error(error?.mensaje ?: response.message)
                        }
                    }
                }
            }

        }catch(e: Exception){
            Timber.e(e)
            return NetworkResult.Error(Constantes.ERROR_ + e.message)

        }
    }

    suspend fun register(usuario: Usuario): NetworkResult<Unit>{
        try{
            val response: NetworkResult<UsuarioResponseItem> = safeApiCall{
                authApi.register(UsuarioResponseItem(usuario.email, usuario.nombre, usuario.usuario, usuario.password))
            }
            return when(response){
                is NetworkResult.Success -> {
                    NetworkResult.Success(Unit)

                }
                is NetworkResult.Error -> {
                    val error = parseErrorResponse(response.message, gson)
                    when(error?.codigo){
                        "USER_EXISTE" -> NetworkResult.Error("El usuario ya esta registrado")
                        "EMAIL_EXISTE" -> NetworkResult.Error("El email ya esta registrado")
                        else -> NetworkResult.Error(error?.mensaje ?: response.message)
                    }

                }
            }

        }catch (e:Exception){
            Timber.e(e)
            return NetworkResult.Error(Constantes.ERROR_ + e.message)
        }
    }
}