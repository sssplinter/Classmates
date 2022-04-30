package presentation.screens.login_screen

import domain.use_cases.authorization.SaveAuthorizationUseCase
import domain.use_cases.authorization.SignInUseCase
import domain.use_cases.authorization.SignUpUseCase
import domain.use_cases.authorization.UseCaseAuthResult
import domain.use_cases.user.LoadUserInfoUseCase
import domain.use_cases.user.SetUserFullNameUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel

class LoginScreenViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
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
            is LoginScreenContract.Event.OnNoWiFiBtnClick,
            is LoginScreenContract.Event.OnNoAccountOkBtnClick,
            -> {
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
            is UseCaseAuthResult.NoSuchAccount -> LoginScreenContract.LoginScreenState.NoSuchAccount
//            is AuthResult.Failed -> LoginScreenContract.LoginScreenState.NoInternetConnection
            is UseCaseAuthResult.UnAuthorized -> LoginScreenContract.LoginScreenState.Idle
        }
        setState { copy(loginScreenState = authState) }
    }

    private fun setUserInfo(name: String, surname: String) = MainScope().launch {

        temporarilyToken?.let { token ->
            val isSaved = setUserFullNameUseCase(token, name, surname)
            if (isSaved) {
                loginToAccount(token)
                val state = LoginScreenContract.LoginScreenState.Confirmed
                setState { copy(loginScreenState = state) }
            }
        }
    }

    private suspend fun loginToAccount(token: String) {
        saveAuthorizationUseCase(token)
        loadUserInfoUseCase(token)
    }

    private fun returnToLogin() = MainScope().launch {
        val state = LoginScreenContract.LoginScreenState.Idle
        setState { copy(loginScreenState = state) }
    }

    override fun clearState() {
        setState { copy(loginScreenState = LoginScreenContract.LoginScreenState.Idle) }
    }

    enum class SignState { SIGN_IN, SIGN_UP }
}