package domain.use_cases.authorization

import domain.source.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignInUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): UseCaseAuthResult = withContext(Dispatchers.IO) {
        val authResponse = authRepository.signIn(email, password)
        return@withContext when {
            authResponse?.accessToken == null -> UseCaseAuthResult.UnAuthorized
            authResponse.accessToken.isNotEmpty() -> {
                UseCaseAuthResult.Authorized(authResponse.accessToken, authResponse.isConfirmed)
            }
            else -> UseCaseAuthResult.NoSuchAccount
        }
    }
}