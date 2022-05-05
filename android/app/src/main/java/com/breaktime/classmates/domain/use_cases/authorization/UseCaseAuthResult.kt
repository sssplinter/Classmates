package com.breaktime.classmates.domain.use_cases.authorization

sealed class UseCaseAuthResult {
    data class Authorized(val token: String, val isConfirmed: Boolean) : UseCaseAuthResult()
    object UnAuthorized : UseCaseAuthResult()
}