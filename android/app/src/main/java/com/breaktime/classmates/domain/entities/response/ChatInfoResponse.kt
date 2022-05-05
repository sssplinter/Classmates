package com.breaktime.classmates.domain.entities.response

import com.breaktime.classmates.domain.entities.data.ChatInfo

data class ChatInfoResponse(
    val data: List<ChatInfo>,
    val message: String
)