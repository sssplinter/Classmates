package domain.use_cases.chat

import domain.loader.AsyncChatLoader
import domain.loader.AsyncDataLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class FindChatsUseCase(
    private val asyncDataLoader: AsyncDataLoader,
) {
    suspend operator fun invoke(text: String = "") = withContext(Dispatchers.IO) {
        asyncDataLoader.asyncChatLoader.allChats.first().filter { it.name.contains(text, true) }
    }
}