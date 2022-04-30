package domain.source.user

import domain.source.user.remote.UserApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userApiService: UserApiService,
) {
    suspend fun getCurrentUserInfo(token: String) = withContext(Dispatchers.IO) {
        return@withContext userApiService.getProfileInfo(token)
    }

    suspend fun setUserFullName(token: String, name: String, surname: String) = withContext(Dispatchers.IO) {
        val params = UserApiService.UserInfoBody(name, surname)
        return@withContext userApiService.setUserFullName(token, params)
    }
}