package presentation.screens.splash_screen

import domain.use_cases.authorization.CheckAuthorizationUseCase
import domain.use_cases.authorization.UseCaseAuthResult
import domain.use_cases.exception.GetNoConnectionExceptionFlowUseCase
import domain.use_cases.server_connection.StartCheckConnectionWithServerUseCase
import domain.use_cases.server_connection.StopCheckConnectionWithServerUseCase
import domain.use_cases.user_info.LoadProfileInfoUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import presentation.screens.splash_screen.SplashScreenContract.SplashScreenState

class SplashScreenViewModel(
    private val checkAuthorizationUseCase: CheckAuthorizationUseCase,
    private val loadProfileInfoUseCase: LoadProfileInfoUseCase,
    private val getNoConnectionExceptionFlowUseCase: GetNoConnectionExceptionFlowUseCase,
    private val startCheckConnectionWithServerUseCase: StartCheckConnectionWithServerUseCase,
    private val stopCheckConnectionWithServerUseCase: StopCheckConnectionWithServerUseCase,
) : BaseViewModel<SplashScreenContract.Event, SplashScreenContract.State, SplashScreenContract.Effect>() {
    private var authResult: UseCaseAuthResult? = null

    init {
        startCollectionNoConnectionException()
    }

    private fun startCollectionNoConnectionException() = launch {
        getNoConnectionExceptionFlowUseCase.invoke().collect {
            if (it != null) {
                startCheckConnectionWithServerUseCase()
            } else {
                checkIsUserAuthorized()
            }
        }
    }

    override fun createInitialState(): SplashScreenContract.State {
        return SplashScreenContract.State(SplashScreenState.Idle)
    }

    override fun handleEvent(event: SplashScreenContract.Event) {
        when (event) {
            is SplashScreenContract.Event.OnAnimationStart -> {
                runAsyncAuthCheck()
            }
            is SplashScreenContract.Event.OnAnimationEnd -> {
                checkIsUserAuthorized()
            }
        }
    }

    private fun runAsyncAuthCheck() = launch {
        authResult = checkAuthorizationUseCase()
    }

    private fun checkIsUserAuthorized() = launch {
        val authState = when (val authResult = authResult) {
            is UseCaseAuthResult.Authorized -> {
                val isAuthorized = loadProfileInfoUseCase(authResult.token)
                if (isAuthorized) SplashScreenState.Authorized else null
            }
            is UseCaseAuthResult.UnAuthorized -> SplashScreenState.Unauthorized
            else -> null
        }
        authState?.let { state -> setState { copy(state = state) } }
    }

    override fun clearState() {
        authResult = UseCaseAuthResult.UnAuthorized
        stopCheckConnectionWithServerUseCase()
        setState { copy(state = SplashScreenState.Idle) }
    }
}