package com.breaktime.classmates.domain.use_cases.chat_flow_data

import com.breaktime.classmates.domain.loader.AsyncDataLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetChatsFlowUseCase(
    private val asyncDataLoader: AsyncDataLoader,
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        return@withContext asyncDataLoader.asyncChatLoader.allChats
    }
}