package domain.use_cases.chat

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteMessageUseCase {
    suspend operator fun invoke(messageId: Int) = withContext(Dispatchers.IO) {

    }
}