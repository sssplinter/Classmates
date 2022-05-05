package domain.use_cases.chat_flow_data

import domain.loader.AsyncDataLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetChatsFlowUseCase(
    private val asyncDataLoader: AsyncDataLoader,
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        return@withContext asyncDataLoader.asyncChatLoader.allChats
    }
}