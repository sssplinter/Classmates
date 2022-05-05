package com.breaktime.classmates.domain.entities.response

import com.breaktime.classmates.domain.entities.data.UserInfo

data class UsersInfoResponse(
    val `data`: List<UserInfo>,
    val message: String
)