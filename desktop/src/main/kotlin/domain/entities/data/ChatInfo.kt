package domain.entities.data

data class ChatInfo(
    val id: Int,
    val name: String,
    val photoUrl: String,
    val lastMessage: String,
    val lastMessageDate: Int,
    val unreadMessagesAmount: Int,
    var isSelected: Boolean = false // aren't from request
)