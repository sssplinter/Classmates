package presentation.screens.splash_screen

import domain.use_cases.authorization.CheckAuthorizationUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel

class SplashScreenViewModel(
    private val checkAuthorizationUseCase: CheckAuthorizationUseCase,
) :
    BaseViewModel<SplashScreenContract.Event, SplashScreenContract.State, SplashScreenContract.Effect>() {
    private var authResult: AuthResult = AuthResult.UnAuthorized

    override fun createInitialState(): SplashScreenContract.State {
        return SplashScreenContract.State(
            SplashScreenContract.SplashScreenState.Idle
        )
    }

    override fun handleEvent(event: SplashScreenContract.Event) {
        when (event) {
            is SplashScreenContract.Event.OnAnimationStart -> {
                runAuthCheckWithoutStates()
            }
            is SplashScreenContract.Event.OnAnimationEnd -> {
                checkIsUserAuthorized()
            }
            is SplashScreenContract.Event.OnRepeatAuthCheckClick -> {
                runAuthCheckWithStates()
            }
        }
    }

    private fun runAuthCheckWithoutStates() {
        runAuthCheck(false)
    }

    private fun runAuthCheckWithStates() {
        setState { copy(splashScreenState = SplashScreenContract.SplashScreenState.Loading) }
        runAuthCheck(true)
    }

    private fun runAuthCheck(isWithCallback: Boolean) {
        runAsyncAuthCheck().invokeOnCompletion {
            if (isWithCallback)
                checkIsUserAuthorized()
        }
    }

    private fun runAsyncAuthCheck() = MainScope().launch {
        authResult = checkAuthorizationUseCase()
    }

    private fun checkIsUserAuthorized() {
        val authState = when (authResult) {
            is AuthResult.Authorized -> SplashScreenContract.SplashScreenState.Authorized
            is AuthResult.UnAuthorized -> SplashScreenContract.SplashScreenState.Unauthorized
            is AuthResult.Failed -> SplashScreenContract.SplashScreenState.NoInternetConnection
        }
        setState { copy(splashScreenState = authState) }
    }

    override fun clearState() {
        setState { copy(splashScreenState = SplashScreenContract.SplashScreenState.Idle) }
    }

    sealed interface AuthResult {
        object Authorized : AuthResult
        object UnAuthorized : AuthResult
        data class Failed(val errorCode: Int) : AuthResult
    }
}