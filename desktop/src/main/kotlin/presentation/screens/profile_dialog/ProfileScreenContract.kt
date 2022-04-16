package presentation.screens.profile_dialog

import presentation.base.UiEffect
import presentation.base.UiEvent
import presentation.base.UiState

class ProfileScreenContract {
    sealed class Event : UiEvent {
        object OnAddUniversityBtnClick : Event()
        data class OnEditUniversityBtnClick(val universityId: Int) : Event()
        object OnLogOutBtnClick : Event()
        object OnDeleteAccountBtnClick : Event()
        object OnNoInternetBtnClick : Event()
        object OnDialogClose : Event()
    }

    data class State(
        val profileScreenState: ProfileScreenState,
    ) : UiState

    sealed class ProfileScreenState {
        object Idle : ProfileScreenState()
        object Loading : ProfileScreenState()
        object NoInternetConnection : ProfileScreenState()
        object Saved : ProfileScreenState()
        object LoggedOut : ProfileScreenState()
        object Deleted : ProfileScreenState()
    }

    sealed class Effect : UiEffect {
        data class DataLoaded(val name: String): Effect()
    }
}