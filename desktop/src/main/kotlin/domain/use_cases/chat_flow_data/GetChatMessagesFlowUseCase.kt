package domain.use_cases.chat_flow_data

import domain.loader.AsyncDataLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetChatMessagesFlowUseCase(
    private val asyncDataLoader: AsyncDataLoader,
) {
    suspend operator fun invoke(chatId: String) = withContext(Dispatchers.IO) {
        return@withContext asyncDataLoader.asyncChatLoader.getChatMessagesFlow(chatId)
    }
}