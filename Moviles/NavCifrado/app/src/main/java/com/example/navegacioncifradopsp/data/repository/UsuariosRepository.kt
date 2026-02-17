package com.example.navegacioncifradopsp.data.repository

import com.example.navegacioncifradopsp.common.Constantes
import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.model.usuariosModel.UsuarioResponseItem
import com.example.navegacioncifradopsp.data.remote.apiService.UsuariosApi
import javax.inject.Inject

class UsuariosRepository @Inject constructor(private val usuariosApi: UsuariosApi) {


    //Aqui no se por que me sale todo el codigo en gris y me da un monton de errores pero yo creo que esta bien y funciona


    suspend fun getAllUsuarios(): NetworkResult<List<UsuarioResponseItem>>{
        try {
            val usuarios = usuariosApi.getAllUsuarios()
            if(usuarios.isSuccessful){
                return NetworkResult.Success(usuarios.body() ?: emptyList())
            } else {
                return NetworkResult.Error(Constantes.ERROR_ + usuarios.code())
            }
        }catch (e: Exception){
            return NetworkResult.Error(Constantes.ERROR_ + e.message)
        }
    }


    suspend fun getUsuarioById(id: Int): NetworkResult<UsuarioResponseItem>{
        try{
            val usuario = usuariosApi.getById(id)
            if(usuario.isSuccessful){
                return NetworkResult.Success(usuario.body() ?: UsuarioResponseItem())
            } else {
                return NetworkResult.Error(Constantes.ERROR_ + usuario.code())
            }
        }catch (e: Exception){
            return NetworkResult.Error(Constantes.ERROR_ + e.message)
        }

    }

    suspend fun deleteUsuario(id: Int): NetworkResult<Boolean>{
        try {
            if(usuariosApi.deleteUusario(id).isSuccessful){
                return NetworkResult.Success(true)
            } else {
                return NetworkResult.Error(Constantes.ERROR_BORRAR)
            }

        }catch (e: Exception){
            return NetworkResult.Error(Constantes.ERROR_ + e.message)
        }

    }

    suspend fun updateUsuario(user: UsuarioResponseItem): NetworkResult<Boolean> {
        try {
            if(usuariosApi.actualizarUsuario(user.id, user).isSuccessful){
                return NetworkResult.Success(true)
            }else {
                return NetworkResult.Error(Constantes.ERROR_ACTUALIZAR)
            }

        }catch (e: Exception){
            return NetworkResult.Error(Constantes.ERROR_ + e.message)
        }

    }

    suspend fun guardarUsuario(user: UsuarioResponseItem): NetworkResult<Boolean>{
        try {
            if(usuariosApi.guardarUsuario(user).isSuccessful){
                return NetworkResult.Success(true)
            } else {
                return NetworkResult.Error(Constantes.ERROR_GUARDAR)
            }

        } catch (e: Exception){
            return NetworkResult.Error(Constantes.ERROR_ + e.message)
        }

    }

}