package domain.source.people

import domain.source.people.local.PeopleStorage
import domain.source.people.remote.PeopleApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PeopleRepository(
    private val peopleStorage: PeopleStorage,
    private val peopleApiService: PeopleApiService,
) {
    suspend fun getUserInfo() = withContext(Dispatchers.IO) {
        val messageBody = PeopleApiService.PeopleBody()
        peopleApiService.getUserInfo(messageBody)
    }

    suspend fun getAllUsers() = withContext(Dispatchers.IO) {
        val messageBody = PeopleApiService.PeopleBody()
        peopleApiService.getAllUsers(messageBody)
    }

    suspend fun getFriends() = withContext(Dispatchers.IO) {
        val messageBody = PeopleApiService.PeopleBody()
        peopleApiService.getFriends(messageBody)
    }

    suspend fun addFriend() = withContext(Dispatchers.IO) {
        val messageBody = PeopleApiService.PeopleBody()
        peopleApiService.addFriend(messageBody)
    }

    suspend fun removeFriend() = withContext(Dispatchers.IO) {
        val messageBody = PeopleApiService.PeopleBody()
        peopleApiService.removeFriend(messageBody)
    }
}