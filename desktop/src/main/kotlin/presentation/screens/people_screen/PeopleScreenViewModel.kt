package presentation.screens.people_screen

import androidx.compose.runtime.mutableStateListOf
import domain.entities.data.UserInfo
import domain.entities.response.UsersInfoResponse
import presentation.base.BaseViewModel

class PeopleScreenViewModel :
    BaseViewModel<PeopleScreenContract.Event, PeopleScreenContract.State, PeopleScreenContract.Effect>() {
    var peopleList = mutableStateListOf<UserInfo>()

    override fun createInitialState(): PeopleScreenContract.State {
        return PeopleScreenContract.State(PeopleScreenContract.PeopleScreenState.Idle)
    }

    override fun handleEvent(event: PeopleScreenContract.Event) {
        when (event) {
            PeopleScreenContract.Event.OnAddFriendClick -> TODO()
            PeopleScreenContract.Event.OnPersonInfoClick -> TODO()
            PeopleScreenContract.Event.OnRemoveFriendClick -> TODO()
        }
    }

    override fun clearState() {
        setState { copy(peopleScreenState = PeopleScreenContract.PeopleScreenState.Idle) }
    }

}