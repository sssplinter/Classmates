package domain.use_cases.people_flow_data

import domain.loader.AsyncDataLoader
import domain.loader.AsyncPeopleLoader

class SubscriptionsFlowUseCase(
    private val asyncDataLoader: AsyncDataLoader
) {
    operator fun invoke() = asyncDataLoader.asyncPeopleLoader.allSubscriptions
}