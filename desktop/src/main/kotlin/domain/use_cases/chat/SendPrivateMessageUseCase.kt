package domain.use_cases.chat

import domain.source.chat.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendPrivateMessageUseCase(
    private val chatRepository: ChatRepository,
) {
    suspend operator fun invoke(chatId: String, text: String) = withContext(Dispatchers.IO) {
        chatRepository.sendPrivateMessage(chatId, text)
    }
}