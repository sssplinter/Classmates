package presentation.screens.chats_screen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import domain.entities.data.ChatInfo
import domain.entities.data.MessageInfo
import domain.use_cases.chat.FindChatsUseCase
import domain.use_cases.chat.FindMessagesUseCase
import domain.use_cases.chat.GetChatMessagesUseCase
import domain.use_cases.chat.SendMessageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.base.BaseViewModel
import util.getListIterator

class ChatsScreenViewModel(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val findMessagesUseCase: FindMessagesUseCase,
    private val findChatsUseCase: FindChatsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : BaseViewModel<ChatsScreenContract.Event, ChatsScreenContract.State, ChatsScreenContract.Effect>() {
    var chatsList = mutableStateListOf<ChatInfo>()
    var isSearchPanelVisible = mutableStateOf(false)
    val currentChatData = mutableStateOf<Pair<ChatInfo, List<MessageInfo>>?>(null)
    private var findMessagesIterator = emptyList<Int>().getListIterator()

    init {
        searchChats()
    }

    override fun createInitialState(): ChatsScreenContract.State {
        return ChatsScreenContract.State(ChatsScreenContract.DialogsScreenState.Idle)
    }

    override fun handleEvent(event: ChatsScreenContract.Event) {
        when (event) {
            is ChatsScreenContract.Event.OnSelectChat -> selectChat(event.id)
            is ChatsScreenContract.Event.OnOpenCloseSearchClick -> activateSearchPanel()
            is ChatsScreenContract.Event.OnSearchMessageTextAppear -> searchMessages(event.text)
            is ChatsScreenContract.Event.OnSearchChatTextAppear -> searchChats(event.text)
            is ChatsScreenContract.Event.OnSendMessageBtnClick -> sendMessage(event.text)
            is ChatsScreenContract.Event.OnNextFoundMessageBtnClick -> nextFoundMessage()
            is ChatsScreenContract.Event.OnPrevFoundMessageBtnClick -> prevFoundMessage()
        }
    }

    private fun activateSearchPanel() {
        isSearchPanelVisible.value = !isSearchPanelVisible.value
        sendUnFoundEffect()
    }

    private fun searchMessages(text: String) = launch {
        currentChatData.value?.first?.let { chatInfo ->
            findMessagesIterator = findMessagesUseCase(chatInfo.id, text)
            if (findMessagesIterator.hasNext()) {
                sendFoundEffect(true)
            } else {
                sendUnFoundEffect()
            }
        }
    }

    private fun searchChats(text: String = "") = launch {
        val newChats = findChatsUseCase(text)
        withContext(Dispatchers.Main) {
            chatsList.clear()
            chatsList.addAll(newChats)
        }
    }

    private fun sendMessage(text: String) = launch {
        currentChatData.value?.first?.let { chatInfo ->
            sendMessageUseCase(chatInfo.id, text)
        }
    }

    private fun nextFoundMessage() {
        if (findMessagesIterator.hasNext()) {
            sendFoundEffect(true)
        }
    }

    private fun prevFoundMessage() {
        if (findMessagesIterator.hasPrevious()) {
            sendFoundEffect(false)
        }
    }

    private fun sendFoundEffect(isForward: Boolean) {
        setEffect {
            val index = if (isForward) findMessagesIterator.next() else findMessagesIterator.previous()
            ChatsScreenContract.Effect.ShowFoundMessage(
                position = index,
                findMessagesIterator.hasPrevious(),
                findMessagesIterator.hasNext(),
            )
        }
    }

    private fun sendUnFoundEffect() {
        setEffect {
            ChatsScreenContract.Effect.ShowFoundMessage(
                position = -1,
                hasPrev = false,
                hasNext = false
            )
        }
    }

    override fun clearState() {
        setState { copy(state = ChatsScreenContract.DialogsScreenState.Idle) }
    }

    private fun selectChat(id: String) {
        activateChat(id)
        selectCurrentMessages(id)
    }

    private fun activateChat(id: String) {
        chatsList.replaceAll {
            it.copy(isSelected = it.id == id)
        }
    }

    private fun selectCurrentMessages(id: String) = launch {
        chatsList.firstOrNull { it.id == id }?.let { chatInfo ->
            getChatMessagesUseCase.invoke(chatInfo.id).collect { list ->
                setEffect { ChatsScreenContract.Effect.UpdateChatData(chatInfo, list) }
            }
        }
    }
}