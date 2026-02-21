package com.example.navegacioncifradopsp.domain.usecase.authUseCase

import com.example.navegacioncifradopsp.common.NetworkResult
import com.example.navegacioncifradopsp.data.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(username: String, password: String): NetworkResult<Unit> {
        return authRepository.login(username, password)
    }
}