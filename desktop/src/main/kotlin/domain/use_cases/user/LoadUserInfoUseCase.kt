package domain.use_cases.user

import domain.entities.data.CurrentUser
import domain.source.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadUserInfoUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(token: String) = withContext(Dispatchers.IO) {
        val userInfoResponse = userRepository.getCurrentUserInfo(token)
        if (userInfoResponse.isSuccessful) {
            userInfoResponse.body()?.data?.let { userInfo ->
                CurrentUser.setData(
                    token = token,
                    userId = userInfo._id,
                    name = userInfo.name,
                    surname = userInfo.surname,
                    email = userInfo.email,
                    bio = userInfo.bio,
                    profileImageUrl = userInfo.profileImageUrl
                )
            }
        }
    }
}