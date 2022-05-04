package domain.use_cases.chat

import domain.loader.AsyncChatLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class GetChatMessagesUseCase(
    private val asyncChatLoader: AsyncChatLoader,
) {
    suspend operator fun invoke(chatId: String) = withContext(Dispatchers.IO) {
        return@withContext asyncChatLoader.getChatMessagesFlow(chatId)
    }
}