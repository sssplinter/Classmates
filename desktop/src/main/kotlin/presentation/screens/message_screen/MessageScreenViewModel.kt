package presentation.screens.message_screen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import domain.entities.data.ChatInfo
import domain.entities.data.MessageInfo
import domain.use_cases.chat.FindChatsUseCase
import domain.use_cases.chat.FindMessagesUseCase
import domain.use_cases.chat.GetChatMessagesUseCase
import domain.use_cases.chat.SendMessageUseCase
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel

class MessageScreenViewModel(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val findMessagesUseCase: FindMessagesUseCase,
    private val findChatsUseCase: FindChatsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : BaseViewModel<MessageScreenContract.Event, MessageScreenContract.State, MessageScreenContract.Effect>() {
    var chatsList = mutableStateListOf<ChatInfo>().apply {
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
    var isSearchPanelVisible = mutableStateOf(false)
    val currentMessages = mutableStateOf<Pair<ChatInfo, List<MessageInfo>>?>(null)
    var findMessagesIterator = chatsList.listIterator()

    override fun createInitialState(): MessageScreenContract.State {
        return MessageScreenContract.State(MessageScreenContract.MessageScreenState.Idle)
    }

    override fun handleEvent(event: MessageScreenContract.Event) {
        when (event) {
            is MessageScreenContract.Event.OnSelectChat -> selectChat(event.id)
            is MessageScreenContract.Event.OnOpenCloseSearchClick -> activateSearchPanel()
            is MessageScreenContract.Event.OnSearchMessageTextAppear -> searchMessages(event.text)
            is MessageScreenContract.Event.OnSearchChatTextAppear -> searchChats(event.text)
            is MessageScreenContract.Event.OnSendMessageBtnClick -> sendMessage(event.text)
            is MessageScreenContract.Event.OnNextFoundMessageBtnClick -> nextFoundMessage()
            is MessageScreenContract.Event.OnPrevFoundMessageBtnClick -> prevFoundMessage()
        }
    }

    private fun activateSearchPanel() {
        isSearchPanelVisible.value = !isSearchPanelVisible.value
    }

    private fun searchMessages(text: String) = launch {
        findMessagesUseCase(text)
    }

    private fun searchChats(text: String) = launch {
        findChatsUseCase(text)
    }

    private fun sendMessage(text: String) = launch {
        sendMessageUseCase(text)
    }

    private fun nextFoundMessage() {
        if (findMessagesIterator.hasNext()) {
            val foundMessageId = findMessagesIterator.next().id
            sendFoundEffect(foundMessageId)
        }
    }

    private fun prevFoundMessage() {
        if (findMessagesIterator.hasPrevious()) {
            val foundMessageId = findMessagesIterator.previous().id
            sendFoundEffect(foundMessageId)
        }
    }

    private fun sendFoundEffect(messageId: Int) {
        setEffect {
            MessageScreenContract.Effect.ShowFoundMessage(
                id = messageId,
                findMessagesIterator.hasPrevious(),
                findMessagesIterator.hasNext(),
            )
        }
    }

    override fun clearState() {
        setState { copy(messageScreenState = MessageScreenContract.MessageScreenState.Idle) }
    }

    private fun selectChat(id: Int) {
        activateChat(id)
        selectCurrentMessages(id)
    }

    private fun activateChat(id: Int) {
        chatsList.replaceAll {
            it.copy(isSelected = it.id == id)
        }
    }

    private fun selectCurrentMessages(id: Int) = launch {
        currentMessages.value = chatsList.firstOrNull { it.id == id }?.let { chatInfo ->
            val messages = getChatMessagesUseCase.invoke(chatInfo.id)
            chatInfo to messages
        }
    }
}