package presentation.screens.dealogs_screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import util.resetActivationState

fun initDialogsObservable(
    scope: CoroutineScope,
    viewModel: DialogsScreenViewModel,
    messagesListState: LazyListState,
    showNoInternetConnectionDialog: MutableState<Boolean>,
    showLoadingDialog: MutableState<Boolean>,
    findMessageStatus: MutableState<FindMessageStatus>,
) {
    scope.launch {
        viewModel.uiState.collect {
            scope.ensureActive()
            when (it.dialogsScreenState) {
                is DialogsScreenContract.DialogsScreenState.NoInternetConnection -> {
                    resetActivationState(
                        activate = listOf(showNoInternetConnectionDialog),
                        disActivate = listOf(showLoadingDialog)
                    )
                }
                is DialogsScreenContract.DialogsScreenState.Loading -> {
                    resetActivationState(
                        activate = listOf(showNoInternetConnectionDialog, showLoadingDialog),
                    )
                }
                is DialogsScreenContract.DialogsScreenState.Idle -> {
                    resetActivationState(
                        disActivate = listOf(showNoInternetConnectionDialog, showLoadingDialog)
                    )
                }
            }
        }
    }
    scope.launch {
        viewModel.effect.collect {
            scope.ensureActive()
            when (it) {
                is DialogsScreenContract.Effect.ShowFoundMessage -> {
                    findMessageStatus.value = FindMessageStatus(it.position, it.hasNext, it.hasPrev)
                    if (it.position != -1) {
                        messagesListState.animateScrollToItem(it.position, -50)
                    }
                }
                is DialogsScreenContract.Effect.ShowUserProfile -> {

                }
                is DialogsScreenContract.Effect.UpdateChatData -> {
                    if (viewModel.currentChatData.value?.second != it.messagesList) {
                        viewModel.currentChatData.value = it.chatInfo to it.messagesList
                        messagesListState.animateScrollToItem(it.messagesList.lastIndex)
                    }
                }
            }
        }
    }
}