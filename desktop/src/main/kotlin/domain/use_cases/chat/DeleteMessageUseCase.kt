package domain.use_cases.chat

import domain.source.chat.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteMessageUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: Int, messageId: Int) = withContext(Dispatchers.IO) {
        chatRepository.deleteMessage(chatId, messageId)
    }
}