package com.breaktime.classmates.domain.loader

import com.breaktime.classmates.domain.entities.data.UserInfo
import com.breaktime.classmates.domain.loader.AsyncDataLoader.ValueWrapper
import com.breaktime.classmates.domain.source.people.PeopleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val PEOPLE_CHECK_DELAY = 500L
private const val NO_INTERNET_CONNECTION_DELAY = 3000L

class AsyncPeopleLoader(
    val asyncDataLoader: AsyncDataLoader,
    val peopleRepository: PeopleRepository,
) {
    private val _newConnection = MutableStateFlow<Any?>(null)
    val newConnection: StateFlow<Any?> get() = _newConnection.asStateFlow()

    private var isUsersLoading = ValueWrapper(false)
    private val _allUsers: MutableStateFlow<List<UserInfo>> = MutableStateFlow(emptyList())
    val allUsers: StateFlow<List<UserInfo>> get() = _allUsers.asStateFlow()

    private var isFriendsLoading = ValueWrapper(false)
    private val _allFriends: MutableStateFlow<List<UserInfo>> = MutableStateFlow(emptyList())
    val allFriends: StateFlow<List<UserInfo>> get() = _allFriends.asStateFlow()

    private var isSubscriptionsLoading = ValueWrapper(false)
    private val _allSubscriptions: MutableStateFlow<List<UserInfo>> = MutableStateFlow(emptyList())
    val allSubscriptions: StateFlow<List<UserInfo>> get() = _allSubscriptions.asStateFlow()

    private var isSubscribersLoading = ValueWrapper(false)
    private val _allSubscribers: MutableStateFlow<List<UserInfo>> = MutableStateFlow(emptyList())
    val allSubscribers: StateFlow<List<UserInfo>> get() = _allSubscribers.asStateFlow()

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
            { peopleRepository.getSubscribers() },
            _allSubscribers,
            _newConnection,
            PEOPLE_CHECK_DELAY,
            NO_INTERNET_CONNECTION_DELAY
        )
    }
}