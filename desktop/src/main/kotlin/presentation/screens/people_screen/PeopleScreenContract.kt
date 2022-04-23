package presentation.screens.people_screen

import presentation.base.UiEffect
import presentation.base.UiEvent
import presentation.base.UiState

class PeopleScreenContract {
    sealed class Event : UiEvent {
        object OnAddFriendClick : Event()
        object OnRemoveFriendClick : Event()
        object OnSendMessageClick : Event()
        object OnPersonInfoClick : Event()
    }

    data class State(
        val peopleScreenState: PeopleScreenState,
    ) : UiState

    sealed class PeopleScreenState {
        object Idle : PeopleScreenState()
        object NoInternetConnection : PeopleScreenState()
        object Loading : PeopleScreenState()
    }

    sealed class Effect : UiEffect {
        data class PeopleLoaded(val list: List<String>) : Effect()
    }
}