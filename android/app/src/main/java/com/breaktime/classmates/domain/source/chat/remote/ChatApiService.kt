package com.breaktime.classmates.domain.source.chat.remote

import com.breaktime.classmates.domain.entities.data.CurrentUser
import com.breaktime.classmates.domain.entities.response.AuthResponse
import com.breaktime.classmates.domain.entities.response.ChatInfoResponse
import com.breaktime.classmates.domain.entities.response.ChatMessagesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ChatApiService {
    @POST("/sendMessage")
    suspend fun sendMessage(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: ChatBody,
    ): Response<AuthResponse>

    @POST("/getChatsInfo")
    suspend fun getChatsInfo(
        @Header("Access") token: String = CurrentUser.token,
    ): Response<ChatInfoResponse>

    @POST("/getMessages")
    suspend fun getChatMessages(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: ChatBody,
    ): Response<ChatMessagesResponse>

    @POST("/sendPrivateMessage")
    suspend fun sendPrivateMessage(
        @Header("Access") token: String = CurrentUser.token,
        @Body body: ChatBody,
    ): Response<ChatMessagesResponse>

    data class ChatBody(
        val messageText: String = "",
        val chatId: String = "",
        val userId: String = "",
    )
}