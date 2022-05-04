package domain.entities.data

data class UserInfo(
    val _id: String = "",
    val email: String = "",
    val name: String = "",
    val surname: String = "",
    val bio: String = "",
    val profileImageUrl: String = "",
    val userRole: UserRole,
) {
    enum class UserRole(val value: String) {
        ME("me"),
        FRIEND("friend"),
        SUBSCRIBER("subscriber"),
        SUBSCRIPTION("subscription"),
        DEFAULT("default")
    }
}