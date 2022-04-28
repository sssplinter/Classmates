package domain.source.user.remote

import domain.entities.data.CurrentUser
import domain.entities.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.GET

interface UserApiService {
    @GET("/profileInfo")
    suspend fun getProfileInfo(
        @Header("Access") token: String = CurrentUser.token,
    ): Response<UserInfoResponse>
}