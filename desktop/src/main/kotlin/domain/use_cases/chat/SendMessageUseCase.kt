package domain.use_cases.chat

import domain.source.chat.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendMessageUseCase(
    private val chatRepository: ChatRepository,
) {
    suspend operator fun invoke(chatId: Int, text: String) = withContext(Dispatchers.IO) {
        chatRepository.sendMessage(chatId, text)
    }
}