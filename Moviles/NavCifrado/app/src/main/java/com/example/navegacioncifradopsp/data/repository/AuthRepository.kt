package com.example.navegacioncifradopsp.data.repository

import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.remote.apiService.AuthApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApi: AuthApiService) {

    suspend fun login(): NetworkResult<Boolean>{

    }
}