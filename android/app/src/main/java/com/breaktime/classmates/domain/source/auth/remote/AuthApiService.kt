package com.breaktime.classmates.domain.source.auth.remote

import com.breaktime.classmates.domain.entities.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/signup")
    suspend fun signUp(@Body body: AuthBody): Response<AuthResponse>

    @POST("/signin")
    suspend fun signIn(@Body body: AuthBody): Response<AuthResponse>

    data class AuthBody(val email: String, val password: String)
}