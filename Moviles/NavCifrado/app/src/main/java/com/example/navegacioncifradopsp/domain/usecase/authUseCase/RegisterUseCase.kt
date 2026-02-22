package com.example.navegacioncifradopsp.domain.usecase.authUseCase

import com.example.navegacioncifradopsp.data.repository.AuthRepository
import javax.inject.Inject
import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.domain.model.Usuario

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(usuario: Usuario): NetworkResult<Unit>{
        return authRepository.register(usuario)
    }
}