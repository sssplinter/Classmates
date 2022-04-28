package domain.use_cases.chat

import domain.entities.data.MessageInfo
import domain.source.chat.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import util.getListIterator

class FindMessagesUseCase(
    private val chatRepository: ChatRepository,
) {
    suspend operator fun invoke(chatId: String, text: String) = withContext(Dispatchers.IO) {
        val messagesList = chatRepository.allMessages[chatId]?.first()
        val foundMessagesIterator = messagesList?.findTextInMessage(text)
        return@withContext foundMessagesIterator ?: emptyList<Int>().getListIterator()
    }

    private fun List<MessageInfo>.findTextInMessage(text: String): ListIterator<Int> {
        return this.asSequence().withIndex()
            .map { (index, message) ->
                index to message.messageText.contains(text, true)
            }
            .filter { (_, isFoundWord) -> isFoundWord }
            .map { (position, _) -> position }
            .toList()
            .reversed()
            .toList()
            .getListIterator()
    }
}