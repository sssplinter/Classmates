package domain.use_cases.authorization

import domain.source.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteAccountUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(token: String) = withContext(Dispatchers.IO) {
        userRepository.deleteAccount(token)
    }
}