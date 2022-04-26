package domain.entities.data

import com.google.gson.annotations.Expose

data class ChatInfo(
    val id: String,
    val name: String,
    val photoUrl: String,
    val lastMessage: String,
    val lastMessageDate: Int,
    val unreadMessagesAmount: Int,
    @Expose(serialize = false, deserialize = false)
    var isSelected: Boolean = false,
)