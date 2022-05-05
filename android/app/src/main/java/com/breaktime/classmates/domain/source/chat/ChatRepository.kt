package com.breaktime.classmates.domain.source.chat

import com.breaktime.classmates.domain.source.chat.local.ChatStorage
import com.breaktime.classmates.domain.source.chat.remote.ChatApiService
import com.breaktime.classmates.domain.source.checkResponseCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRepository(
    private val chatStorage: ChatStorage,
    private val chatApiService: ChatApiService,
) {
    suspend fun getChats() = withContext(Dispatchers.IO) {
        val response = chatApiService.getChatsInfo()
        response.checkResponseCode()
        return@withContext response.body()?.data ?: emptyList()
    }

    suspend fun getChatMessages(chatId: String) = withContext(Dispatchers.IO) {
        val body = ChatApiService.ChatBody(chatId = chatId)
        val response = chatApiService.getChatMessages(body = body)
        response.checkResponseCode()
        return@withContext response.body()?.data ?: emptyList()
    }

    suspend fun sendMessage(chatId: String, messageText: String) = withContext(Dispatchers.IO) {
        val body = ChatApiService.ChatBody(chatId = chatId, messageText = messageText)
        val response = chatApiService.sendMessage(body = body)
        response.checkResponseCode()
        return@withContext response.isSuccessful
    }

    suspend fun sendPrivateMessage(userId: String, messageText: String) = withContext(Dispatchers.IO) {
        val body = ChatApiService.ChatBody(userId = userId, messageText = messageText)
        val response = chatApiService.sendPrivateMessage(body = body)
        response.checkResponseCode()
        return@withContext response.isSuccessful
    }
}