package domain.entities.response

import domain.entities.data.UserInfo

data class UserInfoResponse(
    val `data`: UserInfo,
    val message: String,
    val status: String,
)