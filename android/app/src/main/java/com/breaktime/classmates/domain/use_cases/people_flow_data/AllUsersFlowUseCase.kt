package com.breaktime.classmates.domain.use_cases.people_flow_data

import com.breaktime.classmates.domain.entities.data.UserInfo
import com.breaktime.classmates.domain.loader.AsyncDataLoader
import kotlinx.coroutines.flow.SharedFlow

class AllUsersFlowUseCase(
    private val asyncDataLoader: AsyncDataLoader
) {
    operator fun invoke(): SharedFlow<List<UserInfo>> {
        return asyncDataLoader.asyncPeopleLoader.allUsers
    }
}