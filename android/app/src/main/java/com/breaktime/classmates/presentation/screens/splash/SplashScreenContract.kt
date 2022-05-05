package com.breaktime.classmates.presentation.screens.splash

import com.breaktime.classmates.presentation.base.UiEffect
import com.breaktime.classmates.presentation.base.UiEvent
import com.breaktime.classmates.presentation.base.UiState

class SplashScreenContract {
    sealed class Event : UiEvent {
        object OnAnimationStart : Event()
        object OnAnimationEnd : Event()
    }

    data class State(val state: SplashScreenState) : UiState

    sealed class SplashScreenState {
        object Idle : SplashScreenState()
        object Authorized : SplashScreenState()
        object Unauthorized : SplashScreenState()
    }

    sealed class Effect : UiEffect
}