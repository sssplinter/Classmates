package com.breaktime.classmates.presentation.screens.message.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.breaktime.classmates.domain.entities.data.ChatInfo
import com.breaktime.classmates.domain.entities.data.MessageInfo
import com.breaktime.classmates.domain.use_cases.chat.FindChatsUseCase
import com.breaktime.classmates.domain.use_cases.chat.FindMessagesUseCase
import com.breaktime.classmates.domain.use_cases.chat.GetChatMessagesUseCase
import com.breaktime.classmates.domain.use_cases.chat.SendMessageUseCase
import com.breaktime.classmates.presentation.base.BaseViewModel
import com.breaktime.classmates.presentation.screens.message.MessageScreenContract
import com.breaktime.classmates.R
class ChatViewModel(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val findMessagesUseCase: FindMessagesUseCase,
    private val findChatsUseCase: FindChatsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : BaseViewModel<MessageScreenContract.Event, MessageScreenContract.State, MessageScreenContract.Effect>() {
    var chatsList = mutableStateListOf<ChatInfo>()
    var isSearchPanelVisible = mutableStateOf(false)
    val currentMessages = mutableStateOf<Pair<ChatInfo, List<MessageInfo>>?>(null)
    private var findMessagesIterator = emptyList<Int>().listIterator()
    override fun createInitialState(): MessageScreenContract.State {
//        TODO("Not yet implemented")
        return MessageScreenContract.State(MessageScreenContract.MessageScreenState.Idle)

    }

    override fun handleEvent(event: MessageScreenContract.Event) {
      //  TODO("Not yet implemented")
    }

    override fun clearState() {
       // TODO("Not yet implemented")
    }

}