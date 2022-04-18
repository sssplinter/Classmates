package domain.source.auth

import domain.source.auth.local.AuthPreferences
import domain.source.auth.remote.AuthApiService
import domain.source.response.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val preferences: AuthPreferences,
    private val authApiService: AuthApiService,
) {
    suspend fun signIn(email: String, password: String): AuthResponse? = withContext(Dispatchers.IO) {
        val response = authApiService.signIn(AuthApiService.AuthBody(email, password))
        if (response.isSuccessful) {
            return@withContext response.body()!!
        } else return@withContext null
    }

    suspend fun signUp(email: String, password: String): AuthResponse? = withContext(Dispatchers.IO) {
        val response = authApiService.signUp(AuthApiService.AuthBody(email, password))
        if (response.isSuccessful) {
            return@withContext response.body()!!
        } else return@withContext null
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