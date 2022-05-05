package com.breaktime.classmates.domain.source.user.remote

import com.breaktime.classmates.domain.entities.data.CurrentUser
import com.breaktime.classmates.domain.entities.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

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

    data class UserInfoBody(
        val name: String = "",
        val surname: String = "",
        val bio: String = "",
    )
}