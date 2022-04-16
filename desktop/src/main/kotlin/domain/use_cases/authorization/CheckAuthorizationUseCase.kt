package domain.use_cases.authorization

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import presentation.screens.splash_screen.SplashScreenViewModel

class CheckAuthorizationUseCase {
    var isFirstTime = true

    // TODO: 13.04.22 return type should be in use case, not in view model
    suspend operator fun invoke(): SplashScreenViewModel.AuthResult = withContext(Dispatchers.IO) {
        delay(2000)

        if (isFirstTime) {
            isFirstTime = false
            return@withContext SplashScreenViewModel.AuthResult.Failed(300)
        }

        return@withContext SplashScreenViewModel.AuthResult.UnAuthorized
    }
}