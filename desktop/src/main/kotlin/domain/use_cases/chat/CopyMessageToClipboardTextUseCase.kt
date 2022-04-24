package domain.use_cases.chat

import domain.source.chat.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CopyMessageToClipboardTextUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(messageId: Int) = withContext(Dispatchers.IO) {
        chatRepository
    }
}