package domain.source.user

import domain.source.checkResponseCode
import domain.source.user.remote.UserApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userApiService: UserApiService,
) {
    suspend fun getCurrentUserInfo(token: String) = withContext(Dispatchers.IO) {
        val response = userApiService.getProfileInfo(token)
        response.checkResponseCode()
        return@withContext response.body()!!.data
    }

    suspend fun setUserFullName(token: String, name: String, surname: String) = withContext(Dispatchers.IO) {
        val params = UserApiService.UserInfoBody(name, surname)
        val response = userApiService.setUserFullName(token, params)
        response.checkResponseCode()
        return@withContext response.isSuccessful
    }
}