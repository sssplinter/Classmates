package domain.use_cases.user_info

import domain.source.people.PeopleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserInfoUseCase(
    private val peopleRepository: PeopleRepository
) {
    suspend operator fun invoke(userId: String) = withContext(Dispatchers.IO) {
        peopleRepository.getUserInfo(userId)
    }
}