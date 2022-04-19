package domain.source.chat

import domain.source.chat.local.ChatStorage
import domain.source.chat.remote.ChatApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRepository(
    private val chatStorage: ChatStorage,
    private val chatApiService: ChatApiService,
) {
    // TODO: 19.04.22 add db check
    suspend fun sendMessage(token: String, chatId: String, messageText: String) = withContext(Dispatchers.IO) {
        val messageBody = ChatApiService.ChatBody(accessToken = token, chatId = chatId, messageText = messageText)
        chatApiService.sendMessage(messageBody)
    }

    suspend fun deleteMessage(token: String, messageId: Int) = withContext(Dispatchers.IO) {
        val messageBody = ChatApiService.ChatBody(accessToken = token, messageId = messageId)
        chatApiService.deleteMessage(messageBody)
    }

    suspend fun getChatMessages(token: String, chatId: String) = withContext(Dispatchers.IO) {
        val messageBody = ChatApiService.ChatBody(accessToken = token, chatId = chatId)
        chatApiService.getChatMessages(messageBody)
    }

    suspend fun getAllMessages(token: String) = withContext(Dispatchers.IO) {
        val messageBody = ChatApiService.ChatBody(accessToken = token)
        chatApiService.getChatMessages(messageBody)
    }
}