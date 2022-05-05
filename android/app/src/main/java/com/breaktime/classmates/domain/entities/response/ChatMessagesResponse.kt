package com.breaktime.classmates.domain.entities.response

import com.breaktime.classmates.domain.entities.data.MessageInfo

data class ChatMessagesResponse(
    val data: List<MessageInfo>,
    val message: String
)