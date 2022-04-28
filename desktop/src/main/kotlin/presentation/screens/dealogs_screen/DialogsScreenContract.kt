package presentation.screens.dealogs_screen

import domain.entities.data.ChatInfo
import domain.entities.data.MessageInfo
import presentation.base.UiEffect
import presentation.base.UiEvent
import presentation.base.UiState

class DialogsScreenContract {
    sealed class Event : UiEvent {
        data class OnSelectChat(val id: String) : Event()
        object OnOpenCloseSearchClick : Event()
        data class OnSearchChatTextAppear(val text: String) : Event()
        data class OnSearchMessageTextAppear(val text: String) : Event()
        data class OnSendMessageBtnClick(val text: String) : Event()
        object OnNextFoundMessageBtnClick : Event()
        object OnPrevFoundMessageBtnClick : Event()
    }

    data class State(
        val dialogsScreenState: DialogsScreenState,
    ) : UiState

    sealed class DialogsScreenState {
        object Idle : DialogsScreenState()
        object NoInternetConnection : DialogsScreenState()
        object Loading : DialogsScreenState()
    }

    sealed class Effect : UiEffect {
        data class ShowUserProfile(val id: Int) : Effect()
        data class ShowFoundMessage(val position: Int, val hasNext: Boolean, val hasPrev: Boolean) : Effect()
        data class UpdateChatData(val chatInfo: ChatInfo, val messagesList: List<MessageInfo>) : Effect()
    }
}