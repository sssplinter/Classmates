package com.breaktime.classmates.presentation.screens.message

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.breaktime.classmates.domain.entities.data.ChatInfo
import com.breaktime.classmates.domain.entities.data.MessageInfo
import com.breaktime.classmates.domain.use_cases.chat.FindChatsUseCase
import com.breaktime.classmates.domain.use_cases.chat.FindMessagesUseCase
import com.breaktime.classmates.domain.use_cases.chat.GetChatMessagesUseCase
import com.breaktime.classmates.domain.use_cases.chat.SendMessageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.breaktime.classmates.presentation.base.BaseViewModel

class MessageScreenViewModel(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val findMessagesUseCase: FindMessagesUseCase,
    private val findChatsUseCase: FindChatsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : BaseViewModel<MessageScreenContract.Event, MessageScreenContract.State, MessageScreenContract.Effect>() {
    var chatsList = mutableStateListOf<ChatInfo>()
    var isSearchPanelVisible = mutableStateOf(false)
    val currentMessages = mutableStateOf<Pair<ChatInfo, List<MessageInfo>>?>(null)
    private var findMessagesIterator = emptyList<Int>().listIterator()

    init {
        searchChats()
    }

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
        sendUnFoundEffect()
    }

    private fun searchMessages(text: String) = launch {
        currentMessages.value?.first?.let { chatInfo ->
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
        currentMessages.value?.first?.let { chatInfo ->
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
            MessageScreenContract.Effect.ShowFoundMessage(
                position = index,
                findMessagesIterator.hasPrevious(),
                findMessagesIterator.hasNext(),
            )
        }
    }

    private fun sendUnFoundEffect() {
        setEffect {
            MessageScreenContract.Effect.ShowFoundMessage(
                position = -1,
                hasPrev = false,
                hasNext = false
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
//        chatsList.replaceAll {
//            it.copy(isSelected = it.id == id)
//        }
    }

    private fun selectCurrentMessages(id: Int) = launch {
//        currentMessages.value = chatsList.firstOrNull { it.id == id }?.let { chatInfo ->
//            val messages = getChatMessagesUseCase.invoke(chatInfo.id)
//            chatInfo to messages
//        }
    }
}