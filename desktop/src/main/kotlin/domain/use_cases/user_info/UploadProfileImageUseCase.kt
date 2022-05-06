package domain.use_cases.user_info

import domain.source.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UploadProfileImageUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(imagePath: String) = withContext(Dispatchers.IO) {
        userRepository.uploadProfileImage(imagePath)
    }
}