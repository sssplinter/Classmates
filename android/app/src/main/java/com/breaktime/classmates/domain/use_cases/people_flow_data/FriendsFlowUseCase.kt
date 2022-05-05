package com.breaktime.classmates.domain.use_cases.people_flow_data

import com.breaktime.classmates.domain.loader.AsyncDataLoader
import com.breaktime.classmates.domain.loader.AsyncPeopleLoader

class FriendsFlowUseCase(
    private val asyncDataLoader: AsyncDataLoader
) {
    operator fun invoke() = asyncDataLoader.asyncPeopleLoader.allFriends
}