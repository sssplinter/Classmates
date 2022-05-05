package domain.entities.response

import domain.entities.data.ChatInfo

data class ChatInfoResponse(
    val data: List<ChatInfo>,
    val message: String
)