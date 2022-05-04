package domain.use_cases.chat_flow_data

import domain.loader.AsyncChatLoader

class NewMessageFlowNotifierUseCase(
    private val asyncChatLoader: AsyncChatLoader,
) {
    operator fun invoke() = asyncChatLoader.newMessage
}