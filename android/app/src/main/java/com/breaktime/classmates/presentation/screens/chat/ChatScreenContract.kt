package com.breaktime.classmates.presentation.screens.chat

import com.breaktime.classmates.domain.entities.data.ChatInfo
import com.breaktime.classmates.domain.entities.data.MessageInfo
import com.breaktime.classmates.presentation.base.UiEffect
import com.breaktime.classmates.presentation.base.UiEvent
import com.breaktime.classmates.presentation.base.UiState

class ChatScreenContract {
    sealed class Event : UiEvent {
        object OnOpenCloseSearchClick : Event()
        data class OnSearchMessageTextAppear(val text: String) : Event()
        data class OnSendMessageBtnClick(val text: String) : Event()
        object OnNextFoundMessageBtnClick : Event()
        object OnPrevFoundMessageBtnClick : Event()
    }

    data class State(val state: ChatScreenState) : UiState

    sealed class ChatScreenState {
        object Idle : ChatScreenState()
        object Loading : ChatScreenState()
    }

    sealed class Effect : UiEffect {
        data class ShowFoundMessage(val position: Int, val hasNext: Boolean, val hasPrev: Boolean) : Effect()
        data class UpdateChatData(
            val currentChatId: String,
            val chatInfo: ChatInfo,
            val messagesList: List<MessageInfo>,
        ) : Effect()
    }
}