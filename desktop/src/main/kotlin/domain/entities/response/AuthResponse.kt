package domain.entities.response

data class AuthResponse(
    val accessToken: String,
    val isConfirmed: Boolean,
    val message: String
)