package domain.use_cases.authorization

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SignOutUseCase {

    suspend operator fun invoke(): Result = withContext(Dispatchers.IO) {
        delay(1000)
        return@withContext Result.Success
    }

    sealed class Result {
        object Success : Result()
        data class Error(val code: Int) : Result()
    }
}