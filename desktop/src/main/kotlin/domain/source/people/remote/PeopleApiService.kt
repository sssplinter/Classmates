package domain.source.people.remote

import domain.entities.response.UsersInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface PeopleApiService {
    @GET("/sendMessage")
    suspend fun getAllUsers(@Body body: PeopleBody): Response<UsersInfoResponse>

    @GET("/profileInfo")
    suspend fun getUserInfo(@Body body: PeopleBody): Response<UsersInfoResponse>

    @GET("/profileInfo")
    suspend fun getFriends(@Body body: PeopleBody): Response<UsersInfoResponse>

    suspend fun addFriend(@Body body: PeopleBody): Response<UsersInfoResponse>

    suspend fun removeFriend(@Body body: PeopleBody): Response<UsersInfoResponse>


    data class PeopleBody(
        val accessToken: String = "",
    )
}