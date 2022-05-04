package domain.use_cases.chat_flow_data

import domain.loader.AsyncChatLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetChatMessagesFlowUseCase(
    private val asyncChatLoader: AsyncChatLoader,
) {
    suspend operator fun invoke(chatId: String) = withContext(Dispatchers.IO) {
        return@withContext asyncChatLoader.getChatMessagesFlow(chatId)
    }
}