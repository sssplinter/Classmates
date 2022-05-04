package domain.use_cases.people_flow_data

import domain.loader.AsyncPeopleLoader

class FriendsFlowUseCase(
    private val asyncPeopleLoader: AsyncPeopleLoader
) {
    operator fun invoke() = asyncPeopleLoader.allFriends
}