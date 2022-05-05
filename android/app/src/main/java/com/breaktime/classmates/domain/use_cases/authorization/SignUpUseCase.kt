package com.breaktime.classmates.domain.use_cases.authorization

import com.breaktime.classmates.domain.source.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignUpUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): UseCaseAuthResult = withContext(Dispatchers.IO) {
        val authResponse = authRepository.signUp(email, password)
        return@withContext when {
            authResponse.accessToken.isNotEmpty() -> {
                UseCaseAuthResult.Authorized(authResponse.accessToken, false)
            }
            else -> UseCaseAuthResult.UnAuthorized
        }
    }
}