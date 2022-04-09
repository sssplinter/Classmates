package domain.use_cases.authorization

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import presentation.screens.login_screen.LoginScreenViewModel

class SignInUseCase {
    var isFirstTime = true

    suspend operator fun invoke(email: String, password: String):
            LoginScreenViewModel.AuthResult = withContext(Dispatchers.IO) {
        delay(2000)

        if (isFirstTime) {
            isFirstTime = false
            return@withContext LoginScreenViewModel.AuthResult.Failed(300)
        }

        if (email == "test@test.com" && password == "12345678") {
            return@withContext LoginScreenViewModel.AuthResult.Authorized
        }

        return@withContext LoginScreenViewModel.AuthResult.NoSuchAccount
    }
}