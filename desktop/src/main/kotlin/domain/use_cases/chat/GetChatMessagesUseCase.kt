package domain.use_cases.chat

import domain.source.chat.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class GetChatMessagesUseCase(
    private val chatRepository: ChatRepository,
) {
    suspend operator fun invoke(chatId: Int) = withContext(Dispatchers.IO) {
        return@withContext chatRepository.allMessages[chatId]?.first() ?: emptyList()
    }
}