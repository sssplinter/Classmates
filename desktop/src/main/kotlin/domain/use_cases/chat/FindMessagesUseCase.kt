package domain.use_cases.chat

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FindMessagesUseCase {
    suspend operator fun invoke(text: String) = withContext(Dispatchers.IO) {

    }
}