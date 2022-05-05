package com.breaktime.classmates.domain.use_cases.people_flow_data

import com.breaktime.classmates.domain.loader.AsyncDataLoader

class NewConnectionFlowNotifierUseCase(
    private val asyncDataLoader: AsyncDataLoader
) {
    operator fun invoke() = asyncDataLoader.asyncPeopleLoader.newConnection
}