package com.breaktime.classmates.domain.entities.data

import com.google.gson.annotations.SerializedName

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
        @SerializedName("me")
        ME("me"),

        @SerializedName("friend")
        FRIEND("friend"),

        @SerializedName("subscriber")
        SUBSCRIBER("subscriber"),

        @SerializedName("subscription")
        SUBSCRIPTION("subscription"),

        @SerializedName("default")
        DEFAULT("default")
    }
}