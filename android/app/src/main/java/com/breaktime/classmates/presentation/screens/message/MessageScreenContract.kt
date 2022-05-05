package com.breaktime.classmates.presentation.screens.message

import com.breaktime.classmates.presentation.base.UiEffect
import com.breaktime.classmates.presentation.base.UiEvent
import com.breaktime.classmates.presentation.base.UiState

class MessageScreenContract {
    sealed class Event : UiEvent {
        data class OnSelectChat(val id: Int) : Event()
        object OnOpenCloseSearchClick : Event()
        data class OnSearchChatTextAppear(val text: String) : Event()
        data class OnSearchMessageTextAppear(val text: String) : Event()
        data class OnSendMessageBtnClick(val text: String) : Event()
        object OnNextFoundMessageBtnClick : Event()
        object OnPrevFoundMessageBtnClick : Event()
    }

    data class State(
        val messageScreenState: MessageScreenState,
    ) : UiState

    sealed class MessageScreenState {
        object Idle : MessageScreenState()
        object NoInternetConnection : MessageScreenState()
        object Loading : MessageScreenState()
    }

    sealed class Effect : UiEffect {
        data class ShowUserProfile(val id: Int) : Effect()
        data class ShowFoundMessage(val position: Int, val hasNext: Boolean, val hasPrev: Boolean) : Effect()
    }
}