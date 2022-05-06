package domain.use_cases.people

import domain.loader.AsyncDataLoader
import domain.source.people.PeopleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class GetFriendsUseCase(
    private val asyncDataLoader: AsyncDataLoader
) {
    suspend operator fun invoke() = asyncDataLoader.asyncPeopleLoader.allFriends.first()
}