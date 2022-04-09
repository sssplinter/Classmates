package presentation.screens.splash_screen

import presentation.base.UiEffect
import presentation.base.UiEvent
import presentation.base.UiState

class SplashScreenContract {
    sealed class Event : UiEvent {
        object OnAnimationStart: Event()
        object OnAnimationEnd: Event()
        object OnRepeatAuthCheckClick: Event()
    }

    data class State(
        val splashScreenState: SplashScreenState,
    ) : UiState

    sealed class SplashScreenState {
        object Idle : SplashScreenState()
        object NoInternetConnection : SplashScreenState()
        object Loading : SplashScreenState()
        object Authorized : SplashScreenState()
        object Unauthorized : SplashScreenState()
    }

    sealed class Effect : UiEffect
}