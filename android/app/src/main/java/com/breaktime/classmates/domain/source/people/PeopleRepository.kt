package com.breaktime.classmates.domain.source.people

import com.breaktime.classmates.domain.entities.data.UserInfo
import com.breaktime.classmates.domain.source.checkResponseCode
import com.breaktime.classmates.domain.source.people.local.PeopleStorage
import com.breaktime.classmates.domain.source.people.remote.PeopleApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PeopleRepository(
    private val peopleStorage: PeopleStorage,
    private val peopleApiService: PeopleApiService,
) {
    suspend fun getAllUsers() = withContext(Dispatchers.IO) {
        val response = peopleApiService.getAllUsers()
        response.checkResponseCode()
        return@withContext response.body()?.data ?: emptyList()
    }

    suspend fun getFriends() = withContext(Dispatchers.IO) {
        val response = peopleApiService.getFriends()
        response.checkResponseCode()
        return@withContext response.body()?.data ?: emptyList()
    }

    suspend fun getSubscribers() = withContext(Dispatchers.IO) {
        val response = peopleApiService.getSubscribers()
        response.checkResponseCode()
        return@withContext response.body()?.data ?: emptyList()
    }

    suspend fun getSubscriptions() = withContext(Dispatchers.IO) {
        val response = peopleApiService.getSubscriptions()
        response.checkResponseCode()
        return@withContext response.body()?.data ?: emptyList()
    }

    suspend fun getProfileInfo() = withContext(Dispatchers.IO) {
        val response = peopleApiService.getProfileInfo()
        response.checkResponseCode()
        return@withContext response.body()?.data as UserInfo
    }

    suspend fun getUserInfo(userId: String) = withContext(Dispatchers.IO) {
        val messageBody = PeopleApiService.PeopleBody(userId)
        val response = peopleApiService.getUserInfo(body = messageBody)
        response.checkResponseCode()
        return@withContext response.body()?.data as UserInfo
    }

    suspend fun removeFriend(userId: String) = withContext(Dispatchers.IO) {
        val messageBody = PeopleApiService.PeopleBody(userId)
        val response = peopleApiService.removeFriend(body = messageBody)
        response.checkResponseCode()
        return@withContext response.isSuccessful
    }

    suspend fun sendFriendRequest(userId: String) = withContext(Dispatchers.IO) {
        val messageBody = PeopleApiService.PeopleBody(userId)
        val response = peopleApiService.sendFriendRequest(body = messageBody)
        response.checkResponseCode()
        return@withContext response.isSuccessful
    }

    suspend fun approveFriendRequest(userId: String) = withContext(Dispatchers.IO) {
        val messageBody = PeopleApiService.PeopleBody(userId)
        val response = peopleApiService.approveFriendRequest(body = messageBody)
        response.checkResponseCode()
        return@withContext response.isSuccessful
    }

    suspend fun removeSubscription(userId: String) = withContext(Dispatchers.IO) {
        val messageBody = PeopleApiService.PeopleBody(userId)
        val response = peopleApiService.removeSubscription(body = messageBody)
        response.checkResponseCode()
        return@withContext response.isSuccessful
    }

    suspend fun rejectFriendRequest(userId: String) = withContext(Dispatchers.IO) {
        val messageBody = PeopleApiService.PeopleBody(userId)
        val response = peopleApiService.rejectFriendRequest(body = messageBody)
        response.checkResponseCode()
        return@withContext response.isSuccessful
    }
}