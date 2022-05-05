package com.breaktime.classmates.domain.entities.response

import com.breaktime.classmates.domain.entities.data.UserInfo

data class UserInfoResponse(
    val `data`: UserInfo,
    val message: String
)