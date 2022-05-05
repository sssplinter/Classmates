package com.breaktime.classmates.presentation.screens.login

import com.breaktime.classmates.domain.use_cases.authorization.*
import com.breaktime.classmates.domain.use_cases.user_info.LoadProfileInfoUseCase
import com.breaktime.classmates.presentation.base.BaseViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val loadProfileInfoUseCase: LoadProfileInfoUseCase,
    private val setUserFullNameUseCase: SetUserFullNameUseCase,
    private val saveAuthorizationUseCase: SaveAuthorizationUseCase,
) : BaseViewModel<LoginScreenContract.Event, LoginScreenContract.State, LoginScreenContract.Effect>() {
    private var signState: SignState = SignState.SIGN_IN
    private var authResult: UseCaseAuthResult = UseCaseAuthResult.UnAuthorized
    private var temporarilyToken: String? = null

    override fun createInitialState(): LoginScreenContract.State {
        return LoginScreenContract.State(
            LoginScreenContract.LoginScreenState.Idle
        )
    }

    override fun handleEvent(event: LoginScreenContract.Event) {
        when (event) {
            is LoginScreenContract.Event.OnSwitchToSignInClick -> {
                signState = SignState.SIGN_IN
            }
            is LoginScreenContract.Event.OnSwitchToSignUpClick -> {
                signState = SignState.SIGN_UP
            }
            is LoginScreenContract.Event.OnSignClick -> {
                when (signState) {
                    SignState.SIGN_UP -> signUp(event.email, event.password)
                    SignState.SIGN_IN -> signIn(event.email, event.password)
                }
            }
            is LoginScreenContract.Event.OnConfirmUserDataBtnClick -> {
                setUserInfo(event.name, event.surname)
            }
            is LoginScreenContract.Event.OnBackToLoginBtnClick -> {
                returnToLogin()
            }
        }
    }

    private fun signIn(email: String, password: String) {
        setState { copy(state = LoginScreenContract.LoginScreenState.Loading) }
        MainScope().launch {
            delay(300)
            authResult = signInUseCase(email, password)
        }.invokeOnCompletion { checkAuthState() }
    }

    private fun signUp(email: String, password: String) {
        setState { copy(state = LoginScreenContract.LoginScreenState.Loading) }
        MainScope().launch {
            delay(300)
            authResult = signUpUseCase(email, password)
        }.invokeOnCompletion { checkAuthState() }
    }

    private fun checkAuthState() = launch {
        val authState = when (val authResult = authResult) {
            is UseCaseAuthResult.Authorized -> {
                if (authResult.isConfirmed) {
                    loginToAccount(authResult.token)
                    LoginScreenContract.LoginScreenState.Confirmed
                } else {
                    temporarilyToken = authResult.token
                    LoginScreenContract.LoginScreenState.UserDataConfirmation
                }
            }
            is UseCaseAuthResult.UnAuthorized -> LoginScreenContract.LoginScreenState.Idle
        }
        setState { copy(state = authState) }
    }

    private fun setUserInfo(name: String, surname: String) = MainScope().launch {
        temporarilyToken?.let { token ->
            val isSaved = setUserFullNameUseCase(token, name, surname)
            if (isSaved) {
                loginToAccount(token)
                val state = LoginScreenContract.LoginScreenState.Confirmed
                setState { copy(state = state) }
            }
        }
    }

    private suspend fun loginToAccount(token: String) {
        saveAuthorizationUseCase(token)
        loadProfileInfoUseCase(token)
    }

    private fun returnToLogin() = MainScope().launch {
        signState = SignState.SIGN_IN
        val state = LoginScreenContract.LoginScreenState.Idle
        setState { copy(state = state) }
    }

    override fun clearState() {
        signState = SignState.SIGN_IN
        authResult = UseCaseAuthResult.UnAuthorized
        setState { copy(state = LoginScreenContract.LoginScreenState.Idle) }
    }

    enum class SignState { SIGN_IN, SIGN_UP }
}