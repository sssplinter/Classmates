package domain.entities.response

data class UsersInfoResponse(
    val `data`: List<UserInfo>,
    val message: String,
    val status: String,
) {
    data class UserInfo(
        val _id: String,
        val chats: List<Any>,//remove
        val email: String,
        val groupChats: List<Any>,//remove
        val name: String,
        val patronim: String,//remove
        val surname: String,
//        val bio: String,
//        val isFriend: Boolean,
//        val profileImageUrl: String,
        val university: List<Any>,
    )
}