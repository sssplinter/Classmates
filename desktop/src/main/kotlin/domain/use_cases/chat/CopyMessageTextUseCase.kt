package domain.use_cases.chat

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CopyMessageTextUseCase {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {

    }
}