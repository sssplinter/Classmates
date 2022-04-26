package domain.source.chat

import domain.entities.data.ChatInfo
import domain.entities.data.MessageInfo
import domain.source.chat.local.ChatStorage
import domain.source.chat.remote.ChatApiService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull

const val CHATS_CHECK_DELAY = 500L
const val MESSAGES_CHECK_DELAY = 500L
const val NO_INTERNET_CONNECTION_DELAY = 3000L

@DelicateCoroutinesApi
class ChatRepository(
    private val chatStorage: ChatStorage,
    private val chatApiService: ChatApiService,
) {
    private val _allChats: MutableSharedFlow<List<ChatInfo>> = MutableSharedFlow()
    val allChats = _allChats.asSharedFlow()

    val allMessages = HashMap<String, MutableSharedFlow<List<MessageInfo>>>()

    init {
        launchUpdateChatsList()
        launchUpdateMessagesList()
    }

    private fun launchUpdateChatsList() {
        GlobalScope.launch {
            while (true) {
                try {
                    chatApiService.getChatInfo().body()?.let { it ->
                        _allChats.emit(it.chatInfo)
                    }
                    delay(CHATS_CHECK_DELAY)
                } catch (_: Exception) {
                    println("internet unavailable")
                    delay(NO_INTERNET_CONNECTION_DELAY)
                }
            }
        }
    }

    private fun launchUpdateMessagesList() {
        GlobalScope.launch {
            while (true) {
                try {
                    allChats.firstOrNull()?.forEach { chatInfo ->
                        if (allMessages[chatInfo.id] == null) {
                            allMessages[chatInfo.id] = MutableSharedFlow()
                        }
                        val requestBody = ChatApiService.ChatBody(chatId = chatInfo.id)
                        val response = chatApiService.getChatMessages(body = requestBody)
                        response.body()?.let { it ->
                            allMessages[chatInfo.id]?.emit(it.chatMessages)
                        }
                    }
                    delay(MESSAGES_CHECK_DELAY)
                } catch (_: Exception) {
                    println("internet unavailable")
                    delay(NO_INTERNET_CONNECTION_DELAY)
                }
            }
        }
    }

    suspend fun sendMessage(chatId: String, messageText: String) = withContext(Dispatchers.IO) {
        chatApiService.sendMessage(body = ChatApiService.ChatBody(chatId = chatId, messageText = messageText))
    }

    suspend fun deleteMessage(chatId: Int, messageId: Int) = withContext(Dispatchers.IO) {}
}