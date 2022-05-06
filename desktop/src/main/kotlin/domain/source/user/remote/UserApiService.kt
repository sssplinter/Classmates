package domain.source.user.remote

import domain.entities.data.CurrentUser
import domain.entities.response.UserInfoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface UserApiService {
    @GET("/profileInfo")
    suspend fun getProfileInfo(
        @Header("Access") token: String = CurrentUser.token,
    ): Response<UserInfoResponse>

    @PUT("/updateProfileInfo")
    suspend fun updateProfileInfo(
        @Header("Access") token: String = CurrentUser.token,
        @Body userInfo: UserInfoBody,
    ): Response<UserInfoResponse>

    @PUT("/updateFullName")
    suspend fun setUserFullName(
        @Header("Access") token: String,
        @Body userInfo: UserInfoBody,
    ): Response<Void>

    @Multipart
    @POST("/uploadProfileImage")
    suspend fun uploadProfileImage(
        @Header("Access") token: String = CurrentUser.token,
        @Part image: MultipartBody.Part
    ): Response<Void>

    data class UserInfoBody(
        val name: String = "",
        val surname: String = "",
        val bio: String = "",
    )
}