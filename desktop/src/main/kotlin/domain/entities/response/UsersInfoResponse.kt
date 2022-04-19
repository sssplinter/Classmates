package domain.entities.response

data class UsersInfoResponse(
    val `data`: List<UserInfo>,
    val message: String,
    val status: String,
) {
    data class UserInfo(
        val _id: String,
        val chats: List<Any>,
        val email: String,
        val groupChats: List<Any>,
        val name: String,
        val patronim: String,
        val surname: String,
        val university: List<Any>,
    )
}