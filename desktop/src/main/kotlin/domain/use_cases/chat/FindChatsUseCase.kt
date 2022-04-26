package domain.use_cases.chat

import domain.source.chat.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class FindChatsUseCase(
    private val chatRepository: ChatRepository,
) {
    suspend operator fun invoke(text: String = "") = withContext(Dispatchers.IO) {
        chatRepository.allChats.firstOrNull()?.filter { it.name.contains(text, true) } ?: emptyList()
    }
}