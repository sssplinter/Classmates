package domain.entities.response

import domain.entities.data.MessageInfo

data class ChatMessagesResponse(
    val chatMessages: List<MessageInfo>,
    val message: String,
    val status: String,
)