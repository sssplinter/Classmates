package presentation.screens.splash_screen

import domain.use_cases.authorization.CheckAuthorizationUseCase
import domain.use_cases.authorization.UseCaseAuthResult
import domain.use_cases.user.LoadUserInfoUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.screens.splash_screen.SplashScreenContract.SplashScreenState

class SplashScreenViewModel(
    private val checkAuthorizationUseCase: CheckAuthorizationUseCase,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
) :
    BaseViewModel<SplashScreenContract.Event, SplashScreenContract.State, SplashScreenContract.Effect>() {
    private var authResult: UseCaseAuthResult = UseCaseAuthResult.UnAuthorized

    override fun createInitialState(): SplashScreenContract.State {
        return SplashScreenContract.State(SplashScreenState.Idle)
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
        setState { copy(splashScreenState = SplashScreenState.Loading) }
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

    private fun checkIsUserAuthorized() = launch {
        val authState = when (val authResult = authResult) {
            is UseCaseAuthResult.Authorized -> {
                loadUserInfoUseCase(authResult.token)
                SplashScreenState.Authorized
            }
            is UseCaseAuthResult.UnAuthorized -> SplashScreenState.Unauthorized
            else -> null
        }
        authState?.let { state -> setState { copy(splashScreenState = state) } }
    }

    override fun clearState() {
        setState { copy(splashScreenState = SplashScreenState.Idle) }
    }
}