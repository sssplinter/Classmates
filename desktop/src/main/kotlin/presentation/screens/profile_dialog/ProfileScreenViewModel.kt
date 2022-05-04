package presentation.screens.profile_dialog

import domain.use_cases.authorization.SignOutUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel

class ProfileScreenViewModel(
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
            is ProfileScreenContract.Event.OnDialogClose -> {

            }
        }
    }

    private fun logOut() {
        setState { copy(state = ProfileScreenContract.ProfileScreenState.Loading) }
        MainScope().launch {
            signOutUseCase()
            setState { copy(state = ProfileScreenContract.ProfileScreenState.LoggedOut) }
        }
    }

    override fun clearState() {
        setState { copy(state = ProfileScreenContract.ProfileScreenState.Idle) }
    }
}