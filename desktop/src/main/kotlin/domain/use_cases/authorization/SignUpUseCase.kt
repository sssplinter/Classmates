package domain.use_cases.authorization

import domain.source.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignUpUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): UseCaseAuthResult = withContext(Dispatchers.IO) {
        val authResponse = authRepository.signUp(email, password)
        return@withContext when {
            authResponse?.accessToken == null -> UseCaseAuthResult.UnAuthorized
            authResponse.accessToken.isNotEmpty() -> {
                authRepository.saveAuthorization(authResponse.accessToken)
                UseCaseAuthResult.Authorized(authResponse.accessToken)
            }
            else -> UseCaseAuthResult.NoSuchAccount
        }
    }
}