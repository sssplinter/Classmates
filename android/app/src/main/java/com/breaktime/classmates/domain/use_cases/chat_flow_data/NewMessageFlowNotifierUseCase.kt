package com.breaktime.classmates.domain.use_cases.chat_flow_data

import com.breaktime.classmates.domain.loader.AsyncDataLoader

class NewMessageFlowNotifierUseCase(
    private val asyncDataLoader: AsyncDataLoader,
) {
    operator fun invoke() = asyncDataLoader.asyncChatLoader.newMessage
}