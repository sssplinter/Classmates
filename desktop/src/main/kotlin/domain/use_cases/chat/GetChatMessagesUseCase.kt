package domain.use_cases.chat

import domain.entities.data.MessageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetChatMessagesUseCase {
    suspend operator fun invoke(chatId: Int): List<MessageInfo> = withContext(Dispatchers.IO) {
        return@withContext listOf(
            MessageInfo(1, 1, "message1", 142),
            MessageInfo(2, 2, "message2", 142),
            MessageInfo(3, 3, "message3", 142),
            MessageInfo(4, 4, "message4", 142),
            MessageInfo(5, 1, "message1", 142),
            MessageInfo(6, 2, "message2", 142),
            MessageInfo(7, 3, "message3", 142),
            MessageInfo(8, 4, "message4", 142),
            MessageInfo(9, 1, "message1", 142),
            MessageInfo(10, 2, "message2", 142),
            MessageInfo(11, 3, "message3", 142),
            MessageInfo(12, 4, "message4", 142),
        )
    }
}