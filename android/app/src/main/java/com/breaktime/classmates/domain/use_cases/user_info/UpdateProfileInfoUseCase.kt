package com.breaktime.classmates.domain.use_cases.user_info

import com.breaktime.classmates.domain.exceptions_broadscast.ConnectionExceptionsBroadcast
import com.breaktime.classmates.domain.source.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateProfileInfoUseCase(
    private val userRepository: UserRepository,
    private val exceptionsBroadcast: ConnectionExceptionsBroadcast,
) {
    suspend operator fun invoke(name: String, surname: String, bio: String): Boolean = withContext(Dispatchers.IO) {
        try {
            return@withContext userRepository.updateProfileInfo(name, surname, bio)
        } catch (e: Exception) {
            exceptionsBroadcast.sendException(e)
        }
        return@withContext false
    }
}