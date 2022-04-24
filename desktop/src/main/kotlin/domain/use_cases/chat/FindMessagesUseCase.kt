package domain.use_cases.chat

import domain.source.chat.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class FindMessagesUseCase(
    private val chatRepository: ChatRepository,
) {
    suspend operator fun invoke(chatId: Int, text: String) = withContext(Dispatchers.IO) {
        return@withContext chatRepository.allMessages[chatId]?.first()?.asSequence()?.withIndex()
            ?.map { (index, message) ->
                index to message.text.contains(text, true)
            }?.filter { it.second }?.map { it.first }?.toList()?.reversed()?.listIterator()
            ?: emptyList<Int>().listIterator()
    }
}