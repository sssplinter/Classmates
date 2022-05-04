package domain.loader

import domain.entities.data.UserInfo
import domain.loader.AsyncDataLoader.ValueWrapper
import domain.source.people.PeopleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*

private const val PEOPLE_CHECK_DELAY = 500L
private const val NO_INTERNET_CONNECTION_DELAY = 3000L

class AsyncPeopleLoader(
    private val asyncDataLoader: AsyncDataLoader,
    private val peopleRepository: PeopleRepository,
) : CoroutineScope {
    override var coroutineContext = Dispatchers.IO + SupervisorJob()

    private val _newConnection = MutableStateFlow<Any?>(null)
    val newConnection: StateFlow<Any?> get() = _newConnection.asStateFlow()

    private var isUsersLoading = ValueWrapper(false)
    private val _allUsers: MutableSharedFlow<List<UserInfo>> = MutableSharedFlow()
    val allUsers: SharedFlow<List<UserInfo>> get() = _allUsers.asSharedFlow()

    private var isFriendsLoading = ValueWrapper(false)
    private val _allFriends: MutableSharedFlow<List<UserInfo>> = MutableSharedFlow()
    val allFriends: SharedFlow<List<UserInfo>> get() = _allFriends.asSharedFlow()

    private var isSubscriptionsLoading = ValueWrapper(false)
    private val _allSubscriptions: MutableSharedFlow<List<UserInfo>> = MutableSharedFlow()
    val allSubscriptions: SharedFlow<List<UserInfo>> get() = _allSubscriptions.asSharedFlow()

    private var isSubscribersLoading = ValueWrapper(false)
    private val _allSubscribers: MutableSharedFlow<List<UserInfo>> = MutableSharedFlow()
    val allSubscribers: SharedFlow<List<UserInfo>> get() = _allSubscribers.asSharedFlow()

    fun launchLoading() {
        loadAllUsers()
        loadAllFriends()
        loadAllSubscriptions()
        loadAllSubscribers()
    }

    private fun loadAllUsers() {
        asyncDataLoader.dataLoading(
            isUsersLoading,
            { peopleRepository.getAllUsers() },
            _allUsers,
            null,
            PEOPLE_CHECK_DELAY,
            NO_INTERNET_CONNECTION_DELAY
        )
    }

    private fun loadAllFriends() {
        asyncDataLoader.dataLoading(
            isFriendsLoading,
            { peopleRepository.getFriends() },
            _allFriends,
            _newConnection,
            PEOPLE_CHECK_DELAY,
            NO_INTERNET_CONNECTION_DELAY
        )
    }

    private fun loadAllSubscriptions() {
        asyncDataLoader.dataLoading(
            isSubscriptionsLoading,
            { peopleRepository.getSubscriptions() },
            _allSubscriptions,
            _newConnection,
            PEOPLE_CHECK_DELAY,
            NO_INTERNET_CONNECTION_DELAY
        )
    }

    private fun loadAllSubscribers() {
        asyncDataLoader.dataLoading(
            isSubscribersLoading,
            { peopleRepository.getFriends() },
            _allSubscribers,
            _newConnection,
            PEOPLE_CHECK_DELAY,
            NO_INTERNET_CONNECTION_DELAY
        )
    }
}