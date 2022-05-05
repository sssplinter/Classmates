package com.breaktime.classmates.domain.source.auth

import com.breaktime.classmates.domain.entities.response.AuthResponse
import com.breaktime.classmates.domain.source.auth.local.AuthPreferences
import com.breaktime.classmates.domain.source.auth.remote.AuthApiService
import com.breaktime.classmates.domain.source.checkResponseCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val preferences: AuthPreferences,
    private val authApiService: AuthApiService,
) {
    suspend fun signIn(email: String, password: String): AuthResponse = withContext(Dispatchers.IO) {
        val response = authApiService.signIn(AuthApiService.AuthBody(email, password))
        response.checkResponseCode()
        return@withContext response.body() as AuthResponse
    }

    suspend fun signUp(email: String, password: String): AuthResponse = withContext(Dispatchers.IO) {
        val response = authApiService.signUp(AuthApiService.AuthBody(email, password))
        response.checkResponseCode()
        return@withContext response.body() as AuthResponse
    }

    suspend fun deleteAccount(token: String): AuthResponse? = withContext(Dispatchers.IO) {
        return@withContext null
    }

    suspend fun getSavedToken() = withContext(Dispatchers.IO) {
        preferences.getAuthToken()
    }

    suspend fun saveAuthorization(token: String) = withContext(Dispatchers.IO) {
        preferences.setAuthToken(token)
    }

    suspend fun logOut() = withContext(Dispatchers.IO) {
        preferences.setAuthToken("")
    }
}