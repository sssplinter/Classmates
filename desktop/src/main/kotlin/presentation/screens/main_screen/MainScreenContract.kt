package presentation.screens.main_screen

import presentation.base.UiEffect
import presentation.base.UiEvent
import presentation.base.UiState

class MainScreenContract {
    sealed class Event : UiEvent {
        data class OnMenuButtonClick(val route: String) : Event()
        object OnProfileButtonClick : Event()
        object OnCloseProfileClick : Event()
    }

    data class State(val state: MainScreenState) : UiState

    sealed class MainScreenState {
        object Idle : MainScreenState()
    }

    sealed class Effect : UiEffect {
        data class ChangeFragment(val route: String) : Effect()
        object OpenProfile : Effect()
        object CloseProfile : Effect()
        object NotifyMessages : Effect()
        object NotifyConnections : Effect()
    }
}