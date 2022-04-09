package presentation.screens.login_screen

import presentation.base.UiEffect
import presentation.base.UiEvent
import presentation.base.UiState

class LoginScreenContract {
    sealed class Event : UiEvent {
        object OnSwitchToSignInClick: Event()
        object OnSwitchToSignUpClick: Event()
        data class OnSignClick(val email: String, val password: String): Event()
        object OnNoWiFiBtnClick: Event()
        object OnNoAccountOkBtnClick: Event()
    }

    data class State(
        val loginScreenState: LoginScreenState,
    ) : UiState

    sealed class LoginScreenState {
        object Idle : LoginScreenState()
        object NoInternetConnection : LoginScreenState()
        object NoSuchAccount : LoginScreenState()
        object Loading : LoginScreenState()
        object Authorized : LoginScreenState()
    }

    sealed class Effect : UiEffect
}