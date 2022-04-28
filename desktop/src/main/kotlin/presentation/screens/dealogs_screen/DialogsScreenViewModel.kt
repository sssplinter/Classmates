package presentation.screens.dealogs_screen

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

class DialogsScreenViewModel(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val findMessagesUseCase: FindMessagesUseCase,
    private val findChatsUseCase: FindChatsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : BaseViewModel<DialogsScreenContract.Event, DialogsScreenContract.State, DialogsScreenContract.Effect>() {
    var chatsList = mutableStateListOf<ChatInfo>()
    var isSearchPanelVisible = mutableStateOf(false)
    val currentChatData = mutableStateOf<Pair<ChatInfo, List<MessageInfo>>?>(null)
    private var findMessagesIterator = emptyList<Int>().getListIterator()

    init {
        searchChats()
    }

    override fun createInitialState(): DialogsScreenContract.State {
        return DialogsScreenContract.State(DialogsScreenContract.DialogsScreenState.Idle)
    }

    override fun handleEvent(event: DialogsScreenContract.Event) {
        when (event) {
            is DialogsScreenContract.Event.OnSelectChat -> selectChat(event.id)
            is DialogsScreenContract.Event.OnOpenCloseSearchClick -> activateSearchPanel()
            is DialogsScreenContract.Event.OnSearchMessageTextAppear -> searchMessages(event.text)
            is DialogsScreenContract.Event.OnSearchChatTextAppear -> searchChats(event.text)
            is DialogsScreenContract.Event.OnSendMessageBtnClick -> sendMessage(event.text)
            is DialogsScreenContract.Event.OnNextFoundMessageBtnClick -> nextFoundMessage()
            is DialogsScreenContract.Event.OnPrevFoundMessageBtnClick -> prevFoundMessage()
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
            DialogsScreenContract.Effect.ShowFoundMessage(
                position = index,
                findMessagesIterator.hasPrevious(),
                findMessagesIterator.hasNext(),
            )
        }
    }

    private fun sendUnFoundEffect() {
        setEffect {
            DialogsScreenContract.Effect.ShowFoundMessage(
                position = -1,
                hasPrev = false,
                hasNext = false
            )
        }
    }

    override fun clearState() {
        setState { copy(dialogsScreenState = DialogsScreenContract.DialogsScreenState.Idle) }
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
            getChatMessagesUseCase.invoke(chatInfo.id)?.collect { list ->
                setEffect { DialogsScreenContract.Effect.UpdateChatData(chatInfo, list) }
            }
        }
    }
}