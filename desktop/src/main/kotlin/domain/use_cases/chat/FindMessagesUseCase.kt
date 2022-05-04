package domain.use_cases.chat

import domain.entities.data.MessageInfo
import domain.loader.AsyncChatLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import util.getListIterator

class FindMessagesUseCase(
    private val asyncChatLoader: AsyncChatLoader,
) {
    suspend operator fun invoke(chatId: String, text: String) = withContext(Dispatchers.IO) {
        val messagesList = asyncChatLoader.getChatMessagesFlow(chatId).first()
        return@withContext messagesList.findTextInMessage(text)
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