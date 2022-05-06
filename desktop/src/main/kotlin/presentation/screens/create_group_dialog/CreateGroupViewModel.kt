package presentation.screens.create_group_dialog

import domain.use_cases.chat.CreateGroupChatUseCase
import domain.use_cases.people.GetFriendsUseCase
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel

class CreateGroupViewModel(
    private val createGroupChatUseCase: CreateGroupChatUseCase,
    private val getFriendsUseCase: GetFriendsUseCase,
) : BaseViewModel<CreateGroupContract.Event, CreateGroupContract.State, CreateGroupContract.Effect>() {
    fun loadFriends() = launch {
        val friends = getFriendsUseCase()
        val personItems = friends.map { PersonItem(it._id, "${it.name} ${it.surname}", false) }
        setEffect { CreateGroupContract.Effect.OnUsersLoaded(personItems) }
    }

    override fun createInitialState(): CreateGroupContract.State {
        return CreateGroupContract.State(CreateGroupContract.CreateGroupScreenState.Idle)
    }

    override fun handleEvent(event: CreateGroupContract.Event) {
        when (event) {
            is CreateGroupContract.Event.OnCreateClick -> createGroupChat(event.name, event.message, event.usersId)
        }
    }

    private fun createGroupChat(groupTitle: String, message: String, usersId: List<String>) = launch {
        setState { copy(state = CreateGroupContract.CreateGroupScreenState.Loading) }
        val isSuccessFull = createGroupChatUseCase(groupTitle, message, usersId)
        if (isSuccessFull) {
            setState { copy(state = CreateGroupContract.CreateGroupScreenState.Created) }
        } else {
            setState { copy(state = CreateGroupContract.CreateGroupScreenState.Idle) }
        }
    }

    override fun clearState() {
        setState { copy(state = CreateGroupContract.CreateGroupScreenState.Idle) }
    }

    data class PersonItem(val id: String, val name: String, var isChecked: Boolean)
}