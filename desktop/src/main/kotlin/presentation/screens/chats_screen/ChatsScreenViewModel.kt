package presentation.screens.chats_screen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.AwtWindow
import domain.entities.data.ChatInfo
import domain.entities.data.MessageInfo
import domain.use_cases.chat.FindChatsUseCase
import domain.use_cases.chat.FindMessagesUseCase
import domain.use_cases.chat.GetChatMessagesUseCase
import domain.use_cases.chat.SendMessageUseCase
import domain.use_cases.chat_flow_data.GetChatsFlowUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import util.getListIterator
import java.awt.FileDialog
import java.awt.Frame

class ChatsScreenViewModel(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val findMessagesUseCase: FindMessagesUseCase,
    private val findChatsUseCase: FindChatsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getChatsFlowUseCase: GetChatsFlowUseCase,
) : BaseViewModel<ChatsScreenContract.Event, ChatsScreenContract.State, ChatsScreenContract.Effect>() {
    private var searchText = ""
    var chatsList = mutableStateListOf<ChatInfo>()
    var isSearchPanelVisible = mutableStateOf(false)
    val currentChatData = mutableStateOf<Triple<String, ChatInfo, List<MessageInfo>>?>(null)
    private var findMessagesIterator = emptyList<Int>().getListIterator()

    init {
        getAllChats()
    }

    private fun getAllChats() = MainScope().launch {
        getChatsFlowUseCase().collect { chats ->
            val sortedChats = chats.filter { it.name.contains(searchText, true) }.toMutableList()
            sortedChats.sortByDescending { it.lastMessageDate }
            chatsList.clear()
            chatsList.addAll(sortedChats)
        }
    }

    override fun createInitialState(): ChatsScreenContract.State {
        return ChatsScreenContract.State(ChatsScreenContract.DialogsScreenState.Idle)
    }

    override fun handleEvent(event: ChatsScreenContract.Event) {
        when (event) {
            is ChatsScreenContract.Event.OnSelectChat -> selectCurrentChat(event.id)
            is ChatsScreenContract.Event.OnOpenCloseSearchClick -> activateSearchPanel()
            is ChatsScreenContract.Event.OnSearchMessageTextAppear -> searchMessages(event.text)
            is ChatsScreenContract.Event.OnSearchChatTextAppear -> searchChat(event.text)
            is ChatsScreenContract.Event.OnSendMessageBtnClick -> sendMessage(event.text)
            is ChatsScreenContract.Event.OnSelectFile -> openFileChooser()
            is ChatsScreenContract.Event.OnNextFoundMessageBtnClick -> nextFoundMessage()
            is ChatsScreenContract.Event.OnPrevFoundMessageBtnClick -> prevFoundMessage()
        }
    }

    private fun openFileChooser() {

    }

    private fun activateSearchPanel() {
        isSearchPanelVisible.value = !isSearchPanelVisible.value
        sendUnFoundEffect()
    }

    private fun searchChat(text: String) = launch {
        searchText = text
        val chats = getChatsFlowUseCase().first()
        val sortedChats = chats.filter { it.name.contains(searchText, true) }.toMutableList()
        sortedChats.sortByDescending { it.lastMessageDate }
        chatsList.clear()
        chatsList.addAll(sortedChats)
    }

    private fun searchMessages(text: String) = launch {
        currentChatData.value?.second?.let { chatInfo ->
            findMessagesIterator = findMessagesUseCase(chatInfo.id, text)
            if (findMessagesIterator.hasNext()) {
                sendFoundEffect(true)
            } else {
                sendUnFoundEffect()
            }
        }
    }

    private fun sendMessage(text: String) = launch {
        currentChatData.value?.second?.let { chatInfo ->
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

    private fun selectCurrentChat(id: String) = launch {
        chatsList.firstOrNull { it.id == id }?.let { chatInfo ->
            getChatMessagesUseCase.invoke(chatInfo.id).collect { list ->
                setEffect { ChatsScreenContract.Effect.UpdateChatData(id, chatInfo, list) }
            }
        }
    }
}