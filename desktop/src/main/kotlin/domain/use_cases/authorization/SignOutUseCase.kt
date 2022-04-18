package domain.use_cases.authorization

import domain.source.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignOutUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        userRepository.logOut()
    }
}