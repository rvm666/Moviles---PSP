package com.example.navegacioncifradopsp.data.repository


import com.example.navegacioncifradopsp.common.Constantes
import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.model.produccionesModel.toProduccion
import com.example.navegacioncifradopsp.data.remote.apiService.ProduccionesApi
import com.example.navegacioncifradopsp.domain.model.Produccion
import javax.inject.Inject

class ProduccionesRepository @Inject constructor(private val produccionesApi: ProduccionesApi){


    suspend fun getProduccionById(id: Int): NetworkResult<Produccion>{
        try{
            val produccionResponse = produccionesApi.buscarPorId(id)

            if(produccionResponse.isSuccessful){
                val produccion = produccionResponse.body()
                if(produccion != null){
                    return NetworkResult.Success(produccion.toProduccion())
                } else {
                    return NetworkResult.Error(Constantes.NO_SE_HA_ENCONTRADO)
                }
            } else {
                return NetworkResult.Error(Constantes.ERROR_ + produccionResponse.code())
            }

        } catch (e: Exception){
            return NetworkResult.Error(Constantes.ERROR_ + e.message)
        }

    }

    suspend fun getAll(nombre: String): NetworkResult<List<Produccion>>{
        try{
            val movieResponse = produccionesApi.buscarPorNombre(nombre)
            val producciones = mutableListOf<Produccion>()
            if(movieResponse.isSuccessful){

                return NetworkResult.Success(producciones)
            }else {
                return NetworkResult.Error(Constantes.ERROR_ + movieResponse.code())
            }
        } catch (e: Exception){
            return NetworkResult.Error(Constantes.ERROR_ + e.message)
        }
    }

    suspend fun actualizarProduccion(id: Int, produccion: Produccion): NetworkResult<Boolean>{
        try{
            val produccionResponse = produccionesApi.buscarPorId(id)

            if(produccionResponse.isSuccessful){
                val produccion = produccionResponse.body()
                if(produccion != null){
                    return NetworkResult.Success(true)
                } else {
                    return NetworkResult.Error(Constantes.NO_SE_HA_ENCONTRADO)
                }
            } else {
                return NetworkResult.Error(Constantes.ERROR_ + produccionResponse.code())
            }

        } catch (e: Exception){
            return NetworkResult.Error(Constantes.ERROR_ + e.message)
        }

    }

    suspend fun aniadirProduccion(produccion: Produccion): NetworkResult<Boolean>{
        try{
            val produccionResponse = produccionesApi.buscarPorId(produccion.lanzamiento)

            if(produccionResponse.isSuccessful){
                val produccion = produccionResponse.body()
                if(produccion != null){
                    return NetworkResult.Success(true)
                } else {
                    return NetworkResult.Error(Constantes.NO_SE_HA_ENCONTRADO)
                }
            } else {
                return NetworkResult.Error(Constantes.ERROR_ + produccionResponse.code())
            }

        } catch (e: Exception){
            return NetworkResult.Error(Constantes.ERROR_ + e.message)
        }

    }

    suspend fun borrarProduccion(produccion: Produccion): NetworkResult<Boolean>{
        try{
            val produccionResponse = produccionesApi.buscarPorId(produccion.lanzamiento)

            if(produccionResponse.isSuccessful){
                val produccion = produccionResponse.body()
                if(produccion != null){
                    return NetworkResult.Success(true)
                } else {
                    return NetworkResult.Error(Constantes.NO_SE_HA_ENCONTRADO)
                }
            } else {
                return NetworkResult.Error(Constantes.ERROR_ + produccionResponse.code())
            }

        } catch (e: Exception){
            return NetworkResult.Error(Constantes.ERROR_ + e.message)
        }

    }

}