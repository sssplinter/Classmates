package presentation.screens.chats_screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import util.resetActivationState

fun initChatsObservable(
    scope: CoroutineScope,
    viewModel: ChatsScreenViewModel,
    messagesListState: LazyListState,
    showLoadingDialog: MutableState<Boolean>,
    findMessageStatus: MutableState<FindMessageStatus>,
) {
    scope.launch {
        viewModel.uiState.collect {
            scope.ensureActive()
            when (it.state) {
                is ChatsScreenContract.DialogsScreenState.Loading -> {
                    resetActivationState(
                        activate = listOf(showLoadingDialog),
                    )
                }
                is ChatsScreenContract.DialogsScreenState.Idle -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog)
                    )
                }
            }
        }
    }
    scope.launch {
        viewModel.effect.collect {
            scope.ensureActive()
            when (it) {
                is ChatsScreenContract.Effect.ShowFoundMessage -> {
                    findMessageStatus.value = FindMessageStatus(it.position, it.hasNext, it.hasPrev)
                    if (it.position != -1) {
                        messagesListState.animateScrollToItem(it.position, -50)
                    }
                }
                is ChatsScreenContract.Effect.ShowUserProfile -> {

                }
                is ChatsScreenContract.Effect.UpdateChatData -> {
                    viewModel.currentChatData.value = Triple(it.currentChatId, it.chatInfo, it.messagesList)
                    messagesListState.animateScrollToItem(it.messagesList.lastIndex)
                }
            }
        }
    }
}