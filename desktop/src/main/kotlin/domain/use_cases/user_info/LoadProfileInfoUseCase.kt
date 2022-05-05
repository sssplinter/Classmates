package domain.use_cases.user_info

import domain.entities.data.CurrentUser
import domain.exceptions_broadscast.ConnectionExceptionsBroadcast
import domain.source.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadProfileInfoUseCase(
    private val userRepository: UserRepository,
    private val exceptionsBroadcast: ConnectionExceptionsBroadcast,
) {
    suspend operator fun invoke(token: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val userInfo = userRepository.getCurrentUserInfo(token)
            CurrentUser.setData(
                token = token,
                userId = userInfo._id,
                name = userInfo.name,
                surname = userInfo.surname,
                email = userInfo.email,
                bio = userInfo.bio,
                profileImageUrl = userInfo.profileImageUrl
            )
            return@withContext true
        } catch (e: Exception) {
            exceptionsBroadcast.sendException(e)
        }
        return@withContext false
    }
}