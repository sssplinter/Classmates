package domain.loader

import androidx.compose.runtime.MutableState
import domain.entities.data.ChatInfo
import domain.entities.data.MessageInfo
import domain.source.chat.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private const val CHAT_CHECK_DELAY_CHECK_DELAY = 500L
private const val NO_INTERNET_CONNECTION_DELAY = 3000L

private typealias MessageMapValue = Pair<AsyncDataLoader.ValueWrapper<Boolean>, MutableStateFlow<List<MessageInfo>>>

class AsyncChatLoader(
    private val asyncDataLoader: AsyncDataLoader,
    private val chatRepository: ChatRepository,
) : CoroutineScope {
    override var coroutineContext = Dispatchers.IO + SupervisorJob()

    private val _newMessage = MutableStateFlow<Any?>(null)
    val newMessage: StateFlow<Any?> get() = _newMessage.asStateFlow()

    private var isChatsLoading = AsyncDataLoader.ValueWrapper(false)
    private val _allChats: MutableStateFlow<List<ChatInfo>> = MutableStateFlow(emptyList())
    val allChats: StateFlow<List<ChatInfo>> get() = _allChats.asStateFlow()

    private val _allMessages = HashMap<String, MessageMapValue>()
    fun getChatMessagesFlow(chatId: String) = _allMessages[chatId]?.second ?: throw NullPointerException()

    fun launchLoading() {
        launchUpdateChatsList()
        launchUpdateMessagesList()
    }

    private fun launchUpdateChatsList() {
        asyncDataLoader.dataLoading(isChatsLoading,
            { chatRepository.getChats() },
            _allChats,
            null,
            CHAT_CHECK_DELAY_CHECK_DELAY,
            NO_INTERNET_CONNECTION_DELAY)
    }

    private fun launchUpdateMessagesList() = launch {
        while (true) {
            _allChats.firstOrNull()?.forEach { chatInfo ->
                var messageMapValue = _allMessages[chatInfo.id]
                if (messageMapValue == null) {
                    messageMapValue = AsyncDataLoader.ValueWrapper(false) to MutableStateFlow(emptyList())
                    _allMessages[chatInfo.id] = messageMapValue
                }
                asyncDataLoader.dataLoading(
                    messageMapValue.first,
                    { chatRepository.getChatMessages(chatInfo.id) },
                    messageMapValue.second,
                    _newMessage,
                    CHAT_CHECK_DELAY_CHECK_DELAY,
                    NO_INTERNET_CONNECTION_DELAY
                )
            }
        }
    }
}