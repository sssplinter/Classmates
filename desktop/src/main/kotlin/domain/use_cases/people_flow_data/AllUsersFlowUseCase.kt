package domain.use_cases.people_flow_data

import domain.entities.data.UserInfo
import domain.loader.AsyncDataLoader
import kotlinx.coroutines.flow.SharedFlow

class AllUsersFlowUseCase(
    private val asyncDataLoader: AsyncDataLoader
) {
    operator fun invoke(): SharedFlow<List<UserInfo>> {
        return asyncDataLoader.asyncPeopleLoader.allUsers
    }
}