package domain.use_cases.authorization

import domain.source.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SetUserFullNameUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(token: String, name: String, surname: String) = withContext(Dispatchers.IO) {
        return@withContext userRepository.setUserFullName(token, name, surname)
    }
}