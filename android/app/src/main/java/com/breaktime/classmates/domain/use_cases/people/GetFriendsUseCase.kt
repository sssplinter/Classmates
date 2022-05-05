package com.breaktime.classmates.domain.use_cases.people

import com.breaktime.classmates.domain.source.people.PeopleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFriendsUseCase(
    private val peopleRepository: PeopleRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        peopleRepository.getFriends()
    }
}