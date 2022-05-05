package domain.use_cases.people_flow_data

import domain.loader.AsyncDataLoader

class SubscribersFlowUseCase(
    private val asyncDataLoader: AsyncDataLoader
) {
    operator fun invoke() = asyncDataLoader.asyncPeopleLoader.allSubscribers
}