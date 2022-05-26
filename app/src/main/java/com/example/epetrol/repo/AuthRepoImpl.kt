package com.example.epetrol.repo

import com.example.epetrol.data.SignInRequest
import com.example.epetrol.data.SignUpRequest
import com.example.epetrol.getExceptionMessage
import com.example.epetrol.result.SignInResult
import com.example.epetrol.result.SignOutResult
import com.example.epetrol.result.SignUpResult
import com.example.epetrol.service.AuthService
import com.example.epetrol.service.TokenStorageService
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepoImpl @Inject constructor(
    private val authService: AuthService,
    private val tokenStorageService: TokenStorageService
) : AuthRepo {
    override suspend fun signUp(
        email: String,
        password: String,
        confirmPassword: String
    ): SignUpResult {
        try {
            val request = SignUpRequest(
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
            validateSignUpData(request)?.let { return it }
            authService.signUp(request)
            return SignUpResult.SignedUp
        } catch (e: HttpException) {
            return SignUpResult.Error(e.message())
        } catch (e: SocketTimeoutException) {
            return SignUpResult.Error("Timeout")
        }
    }

    override suspend fun signIn(email: String, password: String): SignInResult {
        return try {
            val tokenResponse = authService.signIn(
                SignInRequest(
                    email = email,
                    password = password
                )
            )
            tokenStorageService.saveToken(tokenResponse.token)
            SignInResult.Authorized
        } catch (e: HttpException) {
            SignInResult.Error(e.getExceptionMessage())
        } catch (e: SocketTimeoutException) {
            SignInResult.Error("Timeout")
        }
    }

    override suspend fun signOut(): SignOutResult {
        return try {
            tokenStorageService.clearToken()
            SignOutResult.Unauthorized
        } catch (e: Exception) {
            SignOutResult.Error(e.getExceptionMessage())
        }
    }

    override suspend fun updateToken(): SignInResult {
        return try {
            val token = tokenStorageService.tokensFlow.first()
            val tokenResponse = authService.updateToken(
                "Bearer $token"
            )
            tokenStorageService.saveToken(tokenResponse.token)
            SignInResult.Authorized
        } catch (e: HttpException) {
            if(e.code() == 403) {
                SignInResult.Unauthorized
            } else {
                SignInResult.Error(e.message())
            }
        } catch (e: SocketTimeoutException) {
            SignInResult.Error("Timeout")
        }
    }

    private fun validateSignUpData(request: SignUpRequest): SignUpResult? = when {
        request.password.length <= 6 -> {
            SignUpResult.Error("Пароль має бути довжини 7 або більше")
        }
        request.password != request.confirmPassword -> {
            SignUpResult.Error("Паролі не співпадають")
        }
        else -> {
            null
        }
    }
}