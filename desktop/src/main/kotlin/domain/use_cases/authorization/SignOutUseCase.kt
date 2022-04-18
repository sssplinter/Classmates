package domain.use_cases.authorization

import domain.source.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignOutUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        authRepository.logOut()
    }
}