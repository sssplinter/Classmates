package domain.source.user

import domain.source.checkResponseCode
import domain.source.user.remote.UserApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class UserRepository(
    private val userApiService: UserApiService,
) {
    suspend fun getCurrentUserInfo(token: String) = withContext(Dispatchers.IO) {
        val response = userApiService.getProfileInfo(token)
        response.checkResponseCode()
        return@withContext response.body()!!.data
    }
    suspend fun updateProfileInfo(name: String, surname: String, bio: String) = withContext(Dispatchers.IO) {
        val params = UserApiService.UserInfoBody(name, surname, bio)
        val response = userApiService.updateProfileInfo(userInfo = params)
        response.checkResponseCode()
        return@withContext response.isSuccessful
    }

    suspend fun setUserFullName(token: String, name: String, surname: String) = withContext(Dispatchers.IO) {
        val params = UserApiService.UserInfoBody(name, surname)
        val response = userApiService.setUserFullName(token, params)
        response.checkResponseCode()
        return@withContext response.isSuccessful
    }

    suspend fun uploadProfileImage(path: String) = withContext(Dispatchers.IO) {
        try {
            val file = File(path)
            val image = MultipartBody.Part.createFormData(
                "image", file.name, RequestBody.create("image/*".toMediaTypeOrNull(), file)
            )
            val response = userApiService.uploadProfileImage(image = image)
            response.checkResponseCode()
            return@withContext response.isSuccessful
        } catch (e: Exception) {
            println(e)
        }
        return@withContext false
    }
}