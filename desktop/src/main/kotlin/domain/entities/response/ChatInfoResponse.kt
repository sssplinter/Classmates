package domain.entities.response

import domain.entities.data.ChatInfo

data class ChatInfoResponse(
    val chatInfo: List<ChatInfo>,
    val message: String,
    val status: String,
)