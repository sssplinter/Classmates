package com.breaktime.classmates.domain.use_cases.chat

import com.breaktime.classmates.domain.loader.AsyncDataLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetChatMessagesUseCase(
    private val asyncDataLoader: AsyncDataLoader,
) {
    suspend operator fun invoke(chatId: String) = withContext(Dispatchers.IO) {
        return@withContext asyncDataLoader.asyncChatLoader.getChatMessagesFlow(chatId)
    }
}