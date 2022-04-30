package domain.use_cases.authorization

import domain.source.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveAuthorizationUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(token: String) = withContext(Dispatchers.IO) {
        authRepository.saveAuthorization(token)
    }
}