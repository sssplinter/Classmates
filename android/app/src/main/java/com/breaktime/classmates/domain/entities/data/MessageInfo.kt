package com.breaktime.classmates.domain.entities.data

data class MessageInfo(
    val _id: String,
    val chatId: String,
    val fromUserId: String,
    val messageText: String,
    val sendDate: Long,
)