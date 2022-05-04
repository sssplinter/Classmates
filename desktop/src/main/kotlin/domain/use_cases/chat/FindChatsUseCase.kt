package domain.use_cases.chat

import domain.loader.AsyncChatLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class FindChatsUseCase(
    private val asyncChatLoader: AsyncChatLoader,
) {
    suspend operator fun invoke(text: String = "") = withContext(Dispatchers.IO) {
        asyncChatLoader.allChats.first().filter { it.name.contains(text, true) }
    }
}