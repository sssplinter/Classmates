package domain.use_cases.chat

import domain.source.chat.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateGroupChatUseCase(
    private val chatRepository: ChatRepository,
) {
    suspend operator fun invoke(groupName: String, message: String, usersId: List<String>) =
        withContext(Dispatchers.IO) {
            try {
                return@withContext chatRepository.createGroupChat(groupName, message, usersId)
            } catch (e: Exception) {
                println(e)
            }
            return@withContext false
        }
}