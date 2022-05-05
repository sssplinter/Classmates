package com.breaktime.classmates.presentation.screens.profile

import com.breaktime.classmates.presentation.base.UiEffect
import com.breaktime.classmates.presentation.base.UiEvent
import com.breaktime.classmates.presentation.base.UiState

class ProfileScreenContract {
    sealed class Event : UiEvent {
        object OnLogOutBtnClick : Event()
        data class OnSaveInfo(val name: String, val surname: String, val bio: String) : Event()
    }

    data class State(val state: ProfileScreenState) : UiState

    sealed class ProfileScreenState {
        object Idle : ProfileScreenState()
        object Loading : ProfileScreenState()
        object Saved : ProfileScreenState()
        object LoggedOut : ProfileScreenState()
    }

    sealed class Effect : UiEffect {
        data class DataLoaded(val name: String, val surname: String, val bio: String, ): Effect()
    }
}