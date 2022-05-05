package com.breaktime.classmates.presentation.screens.login

import com.breaktime.classmates.presentation.base.UiEvent
import com.breaktime.classmates.presentation.base.UiEffect
import com.breaktime.classmates.presentation.base.UiState

class LoginScreenContract {
    sealed class Event : UiEvent {
        object OnSwitchToSignInClick : Event()
        object OnSwitchToSignUpClick : Event()
        data class OnSignClick(val email: String, val password: String) : Event()
        data class OnConfirmUserDataBtnClick(val name: String, val surname: String) : Event()
        object OnBackToLoginBtnClick : Event()
    }

    data class State(val state: LoginScreenState) : UiState

    sealed class LoginScreenState {
        object Idle : LoginScreenState()
        object NoSuchAccount : LoginScreenState()
        object Loading : LoginScreenState()
        object Confirmed : LoginScreenState()
        object UserDataConfirmation : LoginScreenState()
    }

    sealed class Effect : UiEffect
}