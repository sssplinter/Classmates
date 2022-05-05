package com.breaktime.classmates.domain.entities.response

data class AuthResponse(
    val accessToken: String,
    val isConfirmed: Boolean,
    val message: String
)