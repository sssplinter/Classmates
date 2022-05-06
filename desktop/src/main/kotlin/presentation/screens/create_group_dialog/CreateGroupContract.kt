package presentation.screens.create_group_dialog

import presentation.base.UiEffect
import presentation.base.UiEvent
import presentation.base.UiState

class CreateGroupContract {
    sealed class Event : UiEvent {
        data class OnCreateClick(val name: String, val message: String, val usersId: List<String>) : Event()
    }

    data class State(val state: CreateGroupScreenState) : UiState

    sealed class CreateGroupScreenState {
        object Idle : CreateGroupScreenState()
        object Created : CreateGroupScreenState()
        object Loading : CreateGroupScreenState()
    }

    sealed class Effect : UiEffect {
        data class OnUsersLoaded(val users: List<CreateGroupViewModel.PersonItem>): Effect()
    }
}