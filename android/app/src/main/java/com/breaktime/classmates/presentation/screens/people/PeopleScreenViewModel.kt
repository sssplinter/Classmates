package com.breaktime.classmates.presentation.screens.people

import androidx.compose.runtime.mutableStateListOf
import com.breaktime.classmates.domain.entities.data.UserInfo
import com.breaktime.classmates.domain.use_cases.chat.SendPrivateMessageUseCase
import com.breaktime.classmates.domain.use_cases.friendship.*
import com.breaktime.classmates.domain.use_cases.people_flow_data.AllUsersFlowUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.breaktime.classmates.presentation.base.BaseViewModel

class PeopleScreenViewModel(
    private val allUsersFlowUseCase: AllUsersFlowUseCase,
    private val approveFriendRequestUseCase: ApproveFriendRequestUseCase,
    private val removeFriendUseCase: RemoveFriendUseCase,
    private val rejectFriendRequestUseCase: RejectFriendRequestUseCase,
    private val removeSubscriptionUseCase: RemoveSubscriptionUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
    private val sendPrivateMessageUseCase: SendPrivateMessageUseCase,
) : BaseViewModel<PeopleScreenContract.Event, PeopleScreenContract.State, PeopleScreenContract.Effect>() {
    private var searchText = ""
    private val _peopleList = mutableStateListOf<UserInfo>()
    val peopleList = mutableStateListOf<UserInfo>()

    init {
        getAllUsers()
    }

    private fun getAllUsers() = MainScope().launch {
        allUsersFlowUseCase().collect { list ->
            _peopleList.clear()
            _peopleList.addAll(list)
            searchInList()
        }
    }

    override fun createInitialState(): PeopleScreenContract.State {
        return PeopleScreenContract.State(PeopleScreenContract.PeopleScreenState.Idle)
    }

    override fun handleEvent(event: PeopleScreenContract.Event) {
        when (event) {
            is PeopleScreenContract.Event.OnSearchUserTextAppear -> {
                searchText = event.text
                searchInList()
            }
            is PeopleScreenContract.Event.OnAcceptSubscriberClick -> launch { approveFriendRequestUseCase(event.user._id) }
            is PeopleScreenContract.Event.OnAddFriendClick -> launch { sendFriendRequestUseCase(event.user._id) }
            is PeopleScreenContract.Event.OnCancelSubscriberClick -> launch { rejectFriendRequestUseCase(event.user._id) }
            is PeopleScreenContract.Event.OnCancelSubscriptionClick -> launch { removeSubscriptionUseCase(event.user._id) }
            is PeopleScreenContract.Event.OnRemoveFriendClick -> launch { removeFriendUseCase(event.user._id) }
            is PeopleScreenContract.Event.OnShowSendMessageClick -> setEffect {
                PeopleScreenContract.Effect.ShowSendMessageDialog(event.user)
            }
            is PeopleScreenContract.Event.OnSendMessageClick -> launch {
                sendPrivateMessageUseCase(event.user._id, event.message)
            }
        }
    }

    private fun searchInList() {
        peopleList.clear()
        val searchedList = _peopleList.filter { "${it.name} ${it.surname}".contains(searchText, true) }
        peopleList.addAll(searchedList)
    }

    override fun clearState() {
        setState { copy(state = PeopleScreenContract.PeopleScreenState.Idle) }
    }
}