package domain.source.response

data class AuthResponse(
    val accessToken: String?,
    val message: String,
    val status: String
)