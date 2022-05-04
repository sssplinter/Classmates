package presentation.screens.people_screen

import androidx.compose.runtime.mutableStateListOf
import domain.entities.data.UserInfo
import presentation.base.BaseViewModel

class PeopleScreenViewModel :
    BaseViewModel<PeopleScreenContract.Event, PeopleScreenContract.State, PeopleScreenContract.Effect>() {
    var peopleList = mutableStateListOf<UserInfo>().apply {
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.DEFAULT))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.DEFAULT))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.DEFAULT))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.DEFAULT))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.SUBSCRIBER))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.SUBSCRIBER))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.SUBSCRIBER))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.SUBSCRIPTION))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.SUBSCRIPTION))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.DEFAULT))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.DEFAULT))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.DEFAULT))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.FRIEND))
        add(UserInfo(name = "name", surname = "surname", bio = "bio", userRole = UserInfo.UserRole.FRIEND))
    }

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
        setState { copy(state = PeopleScreenContract.PeopleScreenState.Idle) }
    }

}