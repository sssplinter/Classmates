package domain.use_cases.authorization

import domain.source.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignInUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(email: String, password: String): UseCaseAuthResult = withContext(Dispatchers.IO) {
        val authResponse = userRepository.signIn(email, password)
        return@withContext when {
            authResponse?.accessToken == null -> UseCaseAuthResult.UnAuthorized
            authResponse.accessToken.isNotEmpty() -> {
                userRepository.saveAuthorization(authResponse.accessToken)
                UseCaseAuthResult.Authorized(authResponse.accessToken)
            }
            else -> UseCaseAuthResult.NoSuchAccount
        }
    }
}