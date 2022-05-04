package domain.use_cases.chat_flow_data

import domain.loader.AsyncChatLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetChatsFlowUseCase(
    private val asyncChatLoader: AsyncChatLoader,
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        return@withContext asyncChatLoader.allChats
    }
}