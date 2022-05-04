package domain.use_cases.people

import domain.source.people.PeopleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetProfileInfoUseCase(
    private val peopleRepository: PeopleRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        peopleRepository.getProfileInfo()
    }
}