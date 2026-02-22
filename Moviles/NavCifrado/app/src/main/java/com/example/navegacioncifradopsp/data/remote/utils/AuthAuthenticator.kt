package com.example.navegacioncifradopsp.data.remote.utils

import com.example.navegacioncifradopsp.data.model.authentication.LoginResponse
import com.example.navegacioncifradopsp.data.remote.apiService.AuthApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import dagger.Lazy

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val service : Lazy<AuthApiService>,
): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            tokenManager.getRefreshToken().first()
        }
        return runBlocking {
            val newToken = getNewToken(token)

            if (!newToken.isSuccessful || newToken.body() == null) {
                tokenManager.deleteToken()
            }

            newToken.body()?.let {
                tokenManager.saveToken(newToken.body()?.token ?: "", token ?: "")
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.token}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<LoginResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return service.get().refreshToken("Bearer $refreshToken")
    }
}
