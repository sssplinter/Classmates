package domain.entities.data

data class MessageInfo(
    val id: Int,
    val fromId: Int,
    val text: String,
    val date: Int,
)