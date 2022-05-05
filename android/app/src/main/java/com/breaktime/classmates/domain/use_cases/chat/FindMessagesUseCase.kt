package com.breaktime.classmates.domain.use_cases.chat

import com.breaktime.classmates.domain.entities.data.MessageInfo
import com.breaktime.classmates.domain.loader.AsyncDataLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import com.breaktime.classmates.util.getListIterator

class FindMessagesUseCase(
    private val asyncDataLoader: AsyncDataLoader,
) {
    suspend operator fun invoke(chatId: String, text: String) = withContext(Dispatchers.IO) {
        val messagesList = asyncDataLoader.asyncChatLoader.getChatMessagesFlow(chatId).first()
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