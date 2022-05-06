package presentation.screens.profile_dialog

import domain.entities.data.CurrentUser
import domain.use_cases.authorization.SignOutUseCase
import domain.use_cases.user_info.LoadProfileInfoUseCase
import domain.use_cases.user_info.UpdateProfileInfoUseCase
import domain.use_cases.user_info.UploadProfileImageUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel
import java.io.File

class ProfileScreenViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val updateProfileInfoUseCase: UpdateProfileInfoUseCase,
    private val loadProfileInfoUseCase: LoadProfileInfoUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
) :
    BaseViewModel<ProfileScreenContract.Event, ProfileScreenContract.State, ProfileScreenContract.Effect>() {

    override fun createInitialState(): ProfileScreenContract.State {
        return ProfileScreenContract.State(
            ProfileScreenContract.ProfileScreenState.Idle
        )
    }

    override fun handleEvent(event: ProfileScreenContract.Event) {
        when (event) {
            is ProfileScreenContract.Event.OnLogOutBtnClick -> {
                logOut()
            }
            is ProfileScreenContract.Event.UpdateProfileImage -> {
                updateProfileImage(event.path)
            }
            is ProfileScreenContract.Event.OnDialogClose -> {
                launch { updateProfileInfoUseCase(event.name, event.surname, event.bio) }.invokeOnCompletion {
                    launch { loadProfileInfoUseCase(CurrentUser.token) }
                }
            }
        }
    }

    private fun updateProfileImage(path: String) = launch {
        uploadProfileImageUseCase(path)
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