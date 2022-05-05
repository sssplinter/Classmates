package com.breaktime.classmates.domain.use_cases.authorization

import com.breaktime.classmates.domain.source.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckAuthorizationUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): UseCaseAuthResult = withContext(Dispatchers.IO) {
        val token = authRepository.getSavedToken()
        if (token.isNotEmpty()) return@withContext UseCaseAuthResult.Authorized(token, true)
        else return@withContext UseCaseAuthResult.UnAuthorized
    }
}