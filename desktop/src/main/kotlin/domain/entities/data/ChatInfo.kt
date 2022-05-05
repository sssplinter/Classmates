package domain.entities.data

data class ChatInfo(
    val id: String,
    val name: String,
    val photoUrl: String,
    val lastMessage: String,
    val lastMessageDate: Long,
    val unreadMessagesAmount: Int,
)