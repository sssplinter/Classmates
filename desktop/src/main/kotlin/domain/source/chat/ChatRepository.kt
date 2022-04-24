package domain.source.chat

import domain.entities.data.ChatInfo
import domain.entities.data.MessageInfo
import domain.source.chat.local.ChatStorage
import domain.source.chat.remote.ChatApiService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first

const val CHATS_CHECK_DELAY = 500L
const val MESSAGES_CHECK_DELAY = 500L

class ChatRepository(
    private val chatStorage: ChatStorage,
    private val chatApiService: ChatApiService,
) {
    private val _allChats: MutableSharedFlow<List<ChatInfo>> = MutableSharedFlow()
    val allChats = _allChats.asSharedFlow()

    val allMessages = HashMap<Int, MutableSharedFlow<List<MessageInfo>>>()

    init {
        launchUpdateChatsList()
        launchUpdateMessagesList()
    }

    private fun launchUpdateChatsList() {
        GlobalScope.launch {
            while (true) {
                val list = mutableListOf<ChatInfo>().apply {
                    add(ChatInfo(1, "name 1", "", "", 12, 12))
                    add(ChatInfo(2, "name 2", "", "", 12, 12))
                    add(ChatInfo(3, "name 3", "", "", 12, 12))
                    add(ChatInfo(4, "name 4", "", "", 12, 12))
                    add(ChatInfo(5, "name 5", "", "", 12, 12))
                    add(ChatInfo(6, "name 6", "", "", 12, 12))
                    add(ChatInfo(7, "name 7", "", "", 12, 12))
                    add(ChatInfo(8, "name 8", "", "", 12, 12))
                    add(ChatInfo(9, "name 9", "", "", 12, 12))
                    add(ChatInfo(10, "name 10", "", "", 12, 12))
                    add(ChatInfo(11, "name 11", "", "", 12, 12))
                    add(ChatInfo(12, "name 12", "", "", 12, 12))
                    add(ChatInfo(13, "name 13", "", "", 12, 12))
                    add(ChatInfo(14, "name 14", "", "", 12, 12))
                    add(ChatInfo(15, "name 15", "", "", 12, 12))
                }
                _allChats.emit(list.toList())
                delay(CHATS_CHECK_DELAY)
            }
        }
    }

    private fun launchUpdateMessagesList() {
        GlobalScope.launch {
            while (true) {
                allChats.first().forEach { chatInfo ->
                    if (allMessages[chatInfo.id] == null) {
                        allMessages[chatInfo.id] = MutableSharedFlow()
                    }
                    val messages = getChatMessages(chatInfo.id)
                    allMessages[chatInfo.id]?.emit(messages)
                }
                delay(MESSAGES_CHECK_DELAY)
            }
        }
    }

    private suspend fun getChatMessages(chatId: Int) = withContext(Dispatchers.IO) {
//        val messageBody = ChatApiService.ChatBody(accessToken = token, chatId = chatId)
//        chatApiService.getChatMessages(messageBody)
        when (chatId) {
            1 -> return@withContext getList(1)
            2 -> return@withContext getList(2)
            3 -> return@withContext getList(3)
            4 -> return@withContext getList(4)
            5 -> return@withContext getList(5)
            6 -> return@withContext getList(6)
            7 -> return@withContext getList(7)
            else -> getList(-1)
        }
    }

    private fun getList(chatId: Int) = listOf(
        MessageInfo(1, 1, "$chatId message1", 142),
        MessageInfo(2, 2, "$chatId message2", 142),
        MessageInfo(3, 3, "$chatId message3", 142),
        MessageInfo(4, 4, "$chatId message4", 142),
        MessageInfo(5, 1, "$chatId message1", 142),
        MessageInfo(6, 2, "$chatId message2", 142),
        MessageInfo(7, 3, "$chatId message3", 142),
        MessageInfo(8, 4, "$chatId message4", 142),
        MessageInfo(9, 1, "$chatId message1", 142),
        MessageInfo(10, 2, "$chatId message2", 142),
        MessageInfo(11, 3, "$chatId message3", 142),
        MessageInfo(12, 4, "$chatId message4", 142),
    )

    suspend fun sendMessage(chatId: Int, messageText: String) = withContext(Dispatchers.IO) {
        val token = ""
        val messageBody = ChatApiService.ChatBody(accessToken = token, chatId = chatId, messageText = messageText)
        chatApiService.sendMessage(messageBody)
    }

    suspend fun deleteMessage(chatId: Int, messageId: Int) = withContext(Dispatchers.IO) {
        val token = ""
        val messageBody = ChatApiService.ChatBody(accessToken = token, chatId = chatId, messageId = messageId)
        chatApiService.deleteMessage(messageBody)
    }
}