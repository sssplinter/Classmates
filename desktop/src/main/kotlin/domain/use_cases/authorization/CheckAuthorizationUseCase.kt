package domain.use_cases.authorization

import domain.source.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckAuthorizationUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): UseCaseAuthResult = withContext(Dispatchers.IO) {
        val token = userRepository.getSavedToken()
        if (token.isNotEmpty()) return@withContext UseCaseAuthResult.Authorized(token)
        else return@withContext UseCaseAuthResult.UnAuthorized
    }
}