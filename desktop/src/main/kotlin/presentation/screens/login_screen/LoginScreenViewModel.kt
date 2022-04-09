package presentation.screens.login_screen

import domain.use_cases.authorization.SignInUseCase
import domain.use_cases.authorization.SignUpUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel

class LoginScreenViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
) :
    BaseViewModel<LoginScreenContract.Event, LoginScreenContract.State, LoginScreenContract.Effect>() {
    private var signState: SignState = SignState.SIGN_IN
    private var authResult: AuthResult = AuthResult.UnAuthorized
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
            is LoginScreenContract.Event.OnNoWiFiBtnClick,
            is LoginScreenContract.Event.OnNoAccountOkBtnClick -> {
                setState { copy(loginScreenState = LoginScreenContract.LoginScreenState.Idle) }
            }
        }
    }

    private fun signIn(email: String, password: String) {
        setState { copy(loginScreenState = LoginScreenContract.LoginScreenState.Loading) }
        MainScope().launch {
            authResult = signInUseCase(email, password)
        }.invokeOnCompletion { checkAuthState() }
    }

    private fun signUp(email: String, password: String) {
        setState { copy(loginScreenState = LoginScreenContract.LoginScreenState.Loading) }
        MainScope().launch {
            authResult = signUpUseCase(email, password)
        }.invokeOnCompletion { checkAuthState() }
    }

    private fun checkAuthState() {
        val authState = when (authResult) {
            is AuthResult.Authorized -> LoginScreenContract.LoginScreenState.Authorized
            is AuthResult.NoSuchAccount -> LoginScreenContract.LoginScreenState.NoSuchAccount
            is AuthResult.Failed -> LoginScreenContract.LoginScreenState.NoInternetConnection
            is AuthResult.UnAuthorized -> LoginScreenContract.LoginScreenState.Idle
        }

        setState { copy(loginScreenState = authState) }
    }

    override fun clearState() {
        setState { copy(loginScreenState = LoginScreenContract.LoginScreenState.Idle) }
    }

    enum class SignState { SIGN_IN, SIGN_UP }

    sealed interface AuthResult {
        object Authorized : AuthResult
        object UnAuthorized : AuthResult
        object NoSuchAccount : AuthResult
        data class Failed(val errorCode: Int) : AuthResult
    }
}