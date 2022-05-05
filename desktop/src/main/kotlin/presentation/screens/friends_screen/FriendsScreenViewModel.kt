package presentation.screens.friends_screen

import androidx.compose.runtime.mutableStateListOf
import domain.entities.data.UserInfo
import domain.use_cases.chat.SendPrivateMessageUseCase
import domain.use_cases.friendship.*
import domain.use_cases.people_flow_data.FriendsFlowUseCase
import domain.use_cases.people_flow_data.SubscribersFlowUseCase
import domain.use_cases.people_flow_data.SubscriptionsFlowUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.screens.people_screen.PeopleScreenContract

class FriendsScreenViewModel(
    private val friendsFlowUseCase: FriendsFlowUseCase,
    private val subscribersFlowUseCase: SubscribersFlowUseCase,
    private val subscriptionsFlowUseCase: SubscriptionsFlowUseCase,
    private val approveFriendRequestUseCase: ApproveFriendRequestUseCase,
    private val rejectFriendRequestUseCase: RejectFriendRequestUseCase,
    private val removeFriendUseCase: RemoveFriendUseCase,
    private val removeSubscriptionUseCase: RemoveSubscriptionUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
    private val sendPrivateMessageUseCase: SendPrivateMessageUseCase,
) : BaseViewModel<FriendsScreenContract.Event, FriendsScreenContract.State, FriendsScreenContract.Effect>() {
    private var searchText = ""
    private val _friendsList = mutableStateListOf<UserInfo>()
    val friendsList = mutableStateListOf<UserInfo>()
    private val _subscribersList = mutableStateListOf<UserInfo>()
    val subscribersList = mutableStateListOf<UserInfo>()
    private val _subscriptionsList = mutableStateListOf<UserInfo>()
    val subscriptionsList = mutableStateListOf<UserInfo>()

    init {
        getAllFriends()
        getAllSubscribers()
        getAllSubscriptions()
    }

    private fun getAllFriends() = MainScope().launch {
        friendsFlowUseCase().collect { list ->
            _friendsList.clear()
            _friendsList.addAll(list)
            searchInList(_friendsList, friendsList)
        }
    }

    private fun getAllSubscribers() = MainScope().launch {
        subscribersFlowUseCase().collect { list ->
            _subscribersList.clear()
            _subscribersList.addAll(list)
            searchInList(_subscribersList, subscribersList)
        }
    }

    private fun getAllSubscriptions() = MainScope().launch {
        subscriptionsFlowUseCase().collect { list ->
            _subscriptionsList.clear()
            _subscriptionsList.addAll(list)
            searchInList(_subscriptionsList, subscriptionsList)
        }
    }

    override fun createInitialState(): FriendsScreenContract.State {
        return FriendsScreenContract.State(FriendsScreenContract.FriendsScreenState.Idle)
    }

    override fun handleEvent(event: FriendsScreenContract.Event) {
        when (event) {
            is FriendsScreenContract.Event.OnSearchUserTextAppear -> {
                searchText = event.text
                searchInLists()
            }
            is FriendsScreenContract.Event.OnAcceptSubscriberClick -> launch { approveFriendRequestUseCase(event.user._id) }
            is FriendsScreenContract.Event.OnAddFriendClick -> launch { sendFriendRequestUseCase(event.user._id) }
            is FriendsScreenContract.Event.OnCancelSubscriberClick -> launch { rejectFriendRequestUseCase(event.user._id) }
            is FriendsScreenContract.Event.OnCancelSubscriptionClick -> launch { removeSubscriptionUseCase(event.user._id) }
            is FriendsScreenContract.Event.OnRemoveFriendClick -> launch { removeFriendUseCase(event.user._id) }
            is FriendsScreenContract.Event.OnShowSendMessageClick -> setEffect {
                FriendsScreenContract.Effect.ShowSendMessageDialog(event.user)
            }
            is FriendsScreenContract.Event.OnSendMessageClick -> launch {
                sendPrivateMessageUseCase(event.user._id, event.message)
            }
        }
    }

    private fun searchInLists() {
        searchInList(_friendsList, friendsList)
        searchInList(_subscribersList, subscribersList)
        searchInList(_subscriptionsList, subscriptionsList)
    }

    private fun searchInList(from: MutableList<UserInfo>, to: MutableList<UserInfo>) {
        to.clear()
        val searchedList = from.filter { "${it.name} ${it.surname}".contains(searchText, true) }
        to.addAll(searchedList)
    }

    override fun clearState() {
        setState { copy(state = FriendsScreenContract.FriendsScreenState.Idle) }
    }
}