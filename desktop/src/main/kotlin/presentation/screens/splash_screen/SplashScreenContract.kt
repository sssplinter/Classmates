package presentation.screens.splash_screen

import presentation.base.UiEffect
import presentation.base.UiEvent
import presentation.base.UiState

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