package domain.use_cases.people_flow_data

import domain.loader.AsyncPeopleLoader

class SubscriptionsFlowUseCase(
    private val asyncPeopleLoader: AsyncPeopleLoader,
) {
    operator fun invoke() = asyncPeopleLoader.allSubscriptions
}