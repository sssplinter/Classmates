package presentation.screens.profile_dialog

import presentation.base.UiEffect
import presentation.base.UiEvent
import presentation.base.UiState

class ProfileScreenContract {
    sealed class Event : UiEvent {
        object OnLogOutBtnClick : Event()
        data class UpdateProfileImage(val path: String) : Event()
        data class OnDialogClose(val name: String, val surname: String, val bio: String) : Event()
    }

    data class State(val state: ProfileScreenState) : UiState

    sealed class ProfileScreenState {
        object Idle : ProfileScreenState()
        object Loading : ProfileScreenState()
        object Saved : ProfileScreenState()
        object LoggedOut : ProfileScreenState()
    }

    sealed class Effect : UiEffect {
        data class DataLoaded(val name: String) : Effect()
    }
}