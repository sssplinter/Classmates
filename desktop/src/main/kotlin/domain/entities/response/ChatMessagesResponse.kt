package domain.entities.response

import domain.entities.data.MessageInfo

data class ChatMessagesResponse(
    val data: List<MessageInfo>,
    val message: String
)