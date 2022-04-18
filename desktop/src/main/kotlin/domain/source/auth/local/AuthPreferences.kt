package domain.source.auth.local

interface AuthPreferences {
    fun getAuthToken(): String

    fun setAuthToken(authToken: String)
}