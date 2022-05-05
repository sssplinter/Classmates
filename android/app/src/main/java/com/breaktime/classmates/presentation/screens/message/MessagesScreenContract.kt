package com.breaktime.classmates.presentation.screens.message

import com.breaktime.classmates.domain.entities.data.ChatInfo
import com.breaktime.classmates.domain.entities.data.MessageInfo
import com.breaktime.classmates.presentation.base.UiEffect
import com.breaktime.classmates.presentation.base.UiEvent
import com.breaktime.classmates.presentation.base.UiState

class MessagesScreenContract {
    sealed class Event : UiEvent {
        data class OnSelectChat(val id: String) : Event()
        object OnOpenCloseSearchClick : Event()
        data class OnSearchChatTextAppear(val text: String) : Event()
        data class OnSearchMessageTextAppear(val text: String) : Event()
        data class OnSendMessageBtnClick(val text: String) : Event()
        object OnNextFoundMessageBtnClick : Event()
        object OnPrevFoundMessageBtnClick : Event()
    }

    data class State(val state: DialogsScreenState) : UiState

    sealed class DialogsScreenState {
        object Idle : DialogsScreenState()
        object Loading : DialogsScreenState()
    }

    sealed class Effect : UiEffect {
        data class ShowUserProfile(val id: Int) : Effect()
        data class ShowFoundMessage(val position: Int, val hasNext: Boolean, val hasPrev: Boolean) : Effect()
        data class UpdateChatData(
            val currentChatId: String,
            val chatInfo: ChatInfo,
            val messagesList: List<MessageInfo>,
        ) : Effect()
    }
}