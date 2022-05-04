package domain.entities.response

import domain.entities.data.UserInfo

data class UsersInfoResponse(
    val `data`: List<UserInfo>,
    val message: String
)