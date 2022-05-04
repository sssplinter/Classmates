package domain.source.people.remote

import domain.entities.data.CurrentUser
import domain.entities.response.UserInfoResponse
import domain.entities.response.UsersInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface PeopleApiService {
    @POST("/getAllUsers")
    suspend fun getAllUsers(
        @Header("Access") token: String = CurrentUser.token,
    ): Response<UsersInfoResponse>

    @POST("/getAllFriends")
    suspend fun getFriends(
        @Header("Access") token: String = CurrentUser.token,
    ): Response<UsersInfoResponse>

    @POST("/getAllSubscribers")
    suspend fun getSubscribers(
        @Header("Access") token: String = CurrentUser.token,
    ): Response<UsersInfoResponse>

    @POST("/getAllSubscriptions")
    suspend fun getSubscriptions(
        @Header("Access") token: String = CurrentUser.token,
    ): Response<UsersInfoResponse>

    @GET("/profileInfo")
    suspend fun getProfileInfo(
        @Header("Access") token: String = CurrentUser.token,
    ): Response<UserInfoResponse>

    @GET("/userInfo")
    suspend fun getUserInfo(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: PeopleBody,
    ): Response<UserInfoResponse>

    @POST("/removeFriend")
    suspend fun removeFriend(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: PeopleBody,
    ): Response<Unit>

    @POST("/sendFriendRequest")
    suspend fun sendFriendRequest(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: PeopleBody,
    ): Response<Unit>

    @POST("/approveFriendRequest")
    suspend fun approveFriendRequest(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: PeopleBody,
    ): Response<Unit>

    @POST("/rejectFriendRequest")
    suspend fun rejectFriendRequest(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: PeopleBody,
    ): Response<Unit>

    @POST("/removeSubscription")
    suspend fun removeSubscription(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: PeopleBody,
    ): Response<Unit>

    data class PeopleBody(
        val userId: String = "",
    )
}