package domain.use_cases.chat_flow_data

import domain.loader.AsyncDataLoader

class NewMessageFlowNotifierUseCase(
    private val asyncDataLoader: AsyncDataLoader,
) {
    operator fun invoke() = asyncDataLoader.asyncChatLoader.newMessage
}