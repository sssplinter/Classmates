package com.breaktime.classmates.presentation.screens.message

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.breaktime.classmates.domain.entities.data.ChatInfo
import com.breaktime.classmates.domain.entities.data.MessageInfo
import com.breaktime.classmates.domain.use_cases.chat.FindChatsUseCase
import com.breaktime.classmates.domain.use_cases.chat.FindMessagesUseCase
import com.breaktime.classmates.domain.use_cases.chat.GetChatMessagesUseCase
import com.breaktime.classmates.domain.use_cases.chat.SendMessageUseCase
import com.breaktime.classmates.domain.use_cases.chat_flow_data.GetChatsFlowUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import com.breaktime.classmates.presentation.base.BaseViewModel
import com.breaktime.classmates.util.getListIterator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

class MessagesScreenViewModel(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val findMessagesUseCase: FindMessagesUseCase,
    private val findChatsUseCase: FindChatsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getChatsFlowUseCase: GetChatsFlowUseCase,
) : BaseViewModel<MessagesScreenContract.Event, MessagesScreenContract.State, MessagesScreenContract.Effect>() {
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

    override fun createInitialState(): MessagesScreenContract.State {
        return MessagesScreenContract.State(MessagesScreenContract.DialogsScreenState.Idle)
    }

    override fun handleEvent(event: MessagesScreenContract.Event) {
        when (event) {
            is MessagesScreenContract.Event.OnSelectChat -> selectCurrentChat(event.id)
            is MessagesScreenContract.Event.OnOpenCloseSearchClick -> activateSearchPanel()
            is MessagesScreenContract.Event.OnSearchMessageTextAppear -> searchMessages(event.text)
            is MessagesScreenContract.Event.OnSearchChatTextAppear -> searchChat(event.text)
            is MessagesScreenContract.Event.OnSendMessageBtnClick -> sendMessage(event.text)
            is MessagesScreenContract.Event.OnNextFoundMessageBtnClick -> nextFoundMessage()
            is MessagesScreenContract.Event.OnPrevFoundMessageBtnClick -> prevFoundMessage()
        }
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
            MessagesScreenContract.Effect.ShowFoundMessage(
                position = index,
                findMessagesIterator.hasPrevious(),
                findMessagesIterator.hasNext(),
            )
        }
    }

    private fun sendUnFoundEffect() {
        setEffect {
            MessagesScreenContract.Effect.ShowFoundMessage(
                position = -1,
                hasPrev = false,
                hasNext = false
            )
        }
    }

    override fun clearState() {
        setState { copy(state = MessagesScreenContract.DialogsScreenState.Idle) }
    }

    private fun selectCurrentChat(id: String) = launch {
        chatsList.firstOrNull { it.id == id }?.let { chatInfo ->
            getChatMessagesUseCase.invoke(chatInfo.id).collect { list ->
                setEffect { MessagesScreenContract.Effect.UpdateChatData(id, chatInfo, list) }
            }
        }
    }
}