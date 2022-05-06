package domain.entities.data

data class MessageInfo(
    val _id: String,
    val chatId: String,
    val fromUserName: String = "",
    val fromUserId: String,
    val messageText: String,
    val sendDate: Long,
)