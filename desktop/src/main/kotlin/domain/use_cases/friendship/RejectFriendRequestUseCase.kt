package domain.use_cases.friendship

import domain.source.people.PeopleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RejectFriendRequestUseCase(
    private val peopleRepository: PeopleRepository,
) {
    suspend operator fun invoke(userId: String) = withContext(Dispatchers.IO) {
        peopleRepository.rejectFriendRequest(userId)
    }
}