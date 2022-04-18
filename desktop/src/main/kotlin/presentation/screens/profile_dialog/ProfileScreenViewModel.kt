package presentation.screens.profile_dialog

import domain.use_cases.authorization.DeleteAccountUseCase
import domain.use_cases.authorization.SignOutUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel

class ProfileScreenViewModel(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val signOutUseCase: SignOutUseCase,
) :
    BaseViewModel<ProfileScreenContract.Event, ProfileScreenContract.State, ProfileScreenContract.Effect>() {

    override fun createInitialState(): ProfileScreenContract.State {
        return ProfileScreenContract.State(
            ProfileScreenContract.ProfileScreenState.Idle
        )
    }

    override fun handleEvent(event: ProfileScreenContract.Event) {
        when (event) {
            is ProfileScreenContract.Event.OnAddUniversityBtnClick -> {

            }
            is ProfileScreenContract.Event.OnEditUniversityBtnClick -> {

            }
            is ProfileScreenContract.Event.OnLogOutBtnClick -> {
                logOut()
            }
            is ProfileScreenContract.Event.OnDeleteAccountBtnClick -> {
                deleteAccount()
            }
            is ProfileScreenContract.Event.OnDialogClose -> {

            }
            is ProfileScreenContract.Event.OnNoInternetBtnClick -> {
                setState { copy(profileScreenState = ProfileScreenContract.ProfileScreenState.Idle) }
            }
        }
    }

    private fun logOut() {
        setState { copy(profileScreenState = ProfileScreenContract.ProfileScreenState.Loading) }
        MainScope().launch {
            signOutUseCase()
            setState { copy(profileScreenState = ProfileScreenContract.ProfileScreenState.LoggedOut) }
        }
    }

    private fun deleteAccount() {
        setState { copy(profileScreenState = ProfileScreenContract.ProfileScreenState.Loading) }
        MainScope().launch {
            deleteAccountUseCase("")
            setState { copy(profileScreenState = ProfileScreenContract.ProfileScreenState.Deleted) }
        }
    }

    override fun clearState() {
        setState { copy(profileScreenState = ProfileScreenContract.ProfileScreenState.Idle) }
    }
}