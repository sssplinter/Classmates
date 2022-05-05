package com.breaktime.classmates.presentation.screens.splash

import com.breaktime.classmates.domain.entities.data.Languages
import com.breaktime.classmates.domain.entities.data.Themes
import com.breaktime.classmates.domain.entities.data.toAppTheme
import com.breaktime.classmates.domain.use_cases.authorization.CheckAuthorizationUseCase
import com.breaktime.classmates.domain.use_cases.authorization.UseCaseAuthResult
import com.breaktime.classmates.domain.use_cases.exception.GetNoConnectionExceptionFlowUseCase
import com.breaktime.classmates.domain.use_cases.server_connection.StartCheckConnectionWithServerUseCase
import com.breaktime.classmates.domain.use_cases.server_connection.StopCheckConnectionWithServerUseCase
import com.breaktime.classmates.domain.use_cases.settings.GetLanguageUseCase
import com.breaktime.classmates.domain.use_cases.settings.GetThemeUseCase
import com.breaktime.classmates.domain.use_cases.user_info.LoadProfileInfoUseCase
import kotlinx.coroutines.launch
import com.breaktime.classmates.localization.rememberLocalization
import com.breaktime.classmates.presentation.base.BaseViewModel
import com.breaktime.classmates.presentation.screens.splash.SplashScreenContract
import com.breaktime.classmates.presentation.screens.splash.SplashScreenContract.SplashScreenState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

//import com.breaktime.classmates.ui.theme.rememberTheme

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
        getNoConnectionExceptionFlowUseCase().collect {
            if (it != null) {
                startCheckConnectionWithServerUseCase()
            } else {
                checkIsUserAuthorized()
            }
        }
    }

    private fun loadTheme() {
        val theme = getThemeUseCase()
//        theme?.let { rememberTheme(Themes.valueOf(theme).toAppTheme()) }
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