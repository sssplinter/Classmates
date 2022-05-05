package presentation.screens.host_screen

import presentation.base.UiEffect
import presentation.base.UiEvent
import presentation.base.UiState

class HostScreenContract {
    sealed class Event : UiEvent {
        object OnMessagesButtonClick : Event()
        object OnConnectionsButtonClick : Event()
        object OnSearchPeopleButtonClick : Event()
        object OnSettingsButtonClick : Event()
        object OnProfileButtonClick : Event()
    }

    data class State(val state: MainScreenState) : UiState

    sealed class MainScreenState {
        object Idle : MainScreenState()
        object UnAuthorized : MainScreenState()
    }

    sealed class Effect : UiEffect {
        object ShowBadInputDialog : Effect()
        object ShowForbiddenDialog : Effect()
        object ShowNoConnectionDialog : Effect()
        object HideNoConnectionDialog : Effect()
        object ShowNoAuthorizationDialog : Effect()
    }
}