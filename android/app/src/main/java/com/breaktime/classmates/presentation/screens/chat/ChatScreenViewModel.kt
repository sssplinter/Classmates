package com.breaktime.classmates.presentation.screens.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.breaktime.classmates.domain.entities.data.ChatInfo
import com.breaktime.classmates.domain.entities.data.MessageInfo
import com.breaktime.classmates.domain.use_cases.chat.FindMessagesUseCase
import com.breaktime.classmates.domain.use_cases.chat.GetChatMessagesUseCase
import com.breaktime.classmates.domain.use_cases.chat.SendMessageUseCase
import com.breaktime.classmates.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChatScreenViewModel(
    private val chatInfo: ChatInfo,
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val findMessagesUseCase: FindMessagesUseCase
) : BaseViewModel<ChatScreenContract.Event, ChatScreenContract.State, ChatScreenContract.Effect>() {
    var isSearchPanelVisible = mutableStateOf(false)
    val currentMessages = mutableStateListOf<MessageInfo>()
    private var findMessagesIterator = emptyList<Int>().listIterator()

    init {
        subscribeOnCurrentChat()
    }

    private fun subscribeOnCurrentChat() = launch {
        getChatMessagesUseCase.invoke(chatInfo.id).collect { list ->
            setEffect { ChatScreenContract.Effect.UpdateChatData(chatInfo.id, chatInfo, list) }
        }
    }

    override fun createInitialState(): ChatScreenContract.State {
        return ChatScreenContract.State(ChatScreenContract.ChatScreenState.Idle)

    }

    override fun handleEvent(event: ChatScreenContract.Event) {
        when (event) {
            is ChatScreenContract.Event.OnOpenCloseSearchClick -> activateSearchPanel()
            is ChatScreenContract.Event.OnSearchMessageTextAppear -> searchMessages(event.text)
            is ChatScreenContract.Event.OnSendMessageBtnClick -> sendMessage(event.text)
            is ChatScreenContract.Event.OnNextFoundMessageBtnClick -> nextFoundMessage()
            is ChatScreenContract.Event.OnPrevFoundMessageBtnClick -> prevFoundMessage()
        }
    }

    private fun activateSearchPanel() {
        isSearchPanelVisible.value = !isSearchPanelVisible.value
        sendUnFoundEffect()
    }

    private fun searchMessages(text: String) = launch {
        findMessagesIterator = findMessagesUseCase(chatInfo.id, text)
        if (findMessagesIterator.hasNext()) {
            sendFoundEffect(true)
        } else {
            sendUnFoundEffect()
        }
    }

    private fun sendMessage(text: String) = launch {
        sendMessageUseCase(chatInfo.id, text)
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
            val index =
                if (isForward) findMessagesIterator.next() else findMessagesIterator.previous()
            ChatScreenContract.Effect.ShowFoundMessage(
                position = index,
                findMessagesIterator.hasPrevious(),
                findMessagesIterator.hasNext(),
            )
        }
    }

    private fun sendUnFoundEffect() {
        setEffect {
            ChatScreenContract.Effect.ShowFoundMessage(
                position = -1,
                hasPrev = false,
                hasNext = false
            )
        }
    }

    override fun clearState() {
        setState { copy(state = ChatScreenContract.ChatScreenState.Idle) }
    }
}