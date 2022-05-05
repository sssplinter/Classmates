package presentation.screens.splash_screen

import domain.entities.data.Languages
import domain.entities.data.Themes
import domain.entities.data.toAppTheme
import domain.use_cases.authorization.CheckAuthorizationUseCase
import domain.use_cases.authorization.UseCaseAuthResult
import domain.use_cases.exception.GetNoConnectionExceptionFlowUseCase
import domain.use_cases.server_connection.StartCheckConnectionWithServerUseCase
import domain.use_cases.server_connection.StopCheckConnectionWithServerUseCase
import domain.use_cases.settings.GetLanguageUseCase
import domain.use_cases.settings.GetThemeUseCase
import domain.use_cases.user_info.LoadProfileInfoUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import localization.rememberLocalization
import presentation.base.BaseViewModel
import presentation.screens.splash_screen.SplashScreenContract.SplashScreenState
import ui.theme.rememberTheme

class SplashScreenViewModel(
    private val checkAuthorizationUseCase: CheckAuthorizationUseCase,
    private val loadProfileInfoUseCase: LoadProfileInfoUseCase,
    private val getNoConnectionExceptionFlowUseCase: GetNoConnectionExceptionFlowUseCase,
    private val startCheckConnectionWithServerUseCase: StartCheckConnectionWithServerUseCase,
    private val stopCheckConnectionWithServerUseCase: StopCheckConnectionWithServerUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val getThemeUseCase: GetThemeUseCase,
) : BaseViewModel<SplashScreenContract.Event, SplashScreenContract.State, SplashScreenContract.Effect>() {
    private var authResult: UseCaseAuthResult? = null

    init {
        startCollectionNoConnectionException()
        loadTheme()
        loadLanguage()
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

    private fun loadTheme() {
        val theme = getThemeUseCase()
        theme?.let { rememberTheme(Themes.valueOf(theme).toAppTheme()) }
    }

    private fun loadLanguage() {
        val language = getLanguageUseCase()
        language?.let { rememberLocalization(Languages.valueOf(language).language) }
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