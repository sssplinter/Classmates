package com.breaktime.classmates.presentation.screens.friends

import com.breaktime.classmates.domain.entities.data.UserInfo
import com.breaktime.classmates.presentation.base.UiEffect
import com.breaktime.classmates.presentation.base.UiEvent
import com.breaktime.classmates.presentation.base.UiState

class FriendsScreenContract {
    sealed class Event : UiEvent {
        data class OnShowSendMessageClick(val user: UserInfo) : Event()
        data class OnSendMessageClick(val user: UserInfo, val message: String) : Event()
        data class OnAddFriendClick(val user: UserInfo) : Event()
        data class OnRemoveFriendClick(val user: UserInfo) : Event()
        data class OnAcceptSubscriberClick(val user: UserInfo) : Event()
        data class OnCancelSubscriberClick(val user: UserInfo) : Event()
        data class OnCancelSubscriptionClick(val user: UserInfo) : Event()
        data class OnSearchUserTextAppear(val text: String) : Event()
    }

    data class State(val state: FriendsScreenState) : UiState

    sealed class FriendsScreenState {
        object Idle : FriendsScreenState()
        object Loading : FriendsScreenState()
    }

    sealed class Effect : UiEffect {
        data class ShowSendMessageDialog(val user: UserInfo) : Effect()
    }
}