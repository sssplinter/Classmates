package presentation.screens.message_screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import util.resetActivationState

fun initMessageObservable(
    scope: CoroutineScope,
    viewModel: MessageScreenViewModel,
    messagesListState: LazyListState,
    showNoInternetConnectionDialog: MutableState<Boolean>,
    showLoadingDialog: MutableState<Boolean>,
    findMessageStatus: MutableState<FindMessageStatus>,
) {
    scope.launch {
        viewModel.uiState.collect {
            scope.ensureActive()
            when (it.messageScreenState) {
                is MessageScreenContract.MessageScreenState.NoInternetConnection -> {
                    resetActivationState(
                        activate = listOf(showNoInternetConnectionDialog),
                        disActivate = listOf(showLoadingDialog)
                    )
                }
                is MessageScreenContract.MessageScreenState.Loading -> {
                    resetActivationState(
                        activate = listOf(showNoInternetConnectionDialog, showLoadingDialog),
                    )
                }
                is MessageScreenContract.MessageScreenState.Idle -> {
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
                is MessageScreenContract.Effect.ShowFoundMessage -> {
                    findMessageStatus.value = FindMessageStatus(it.position, it.hasNext, it.hasPrev)
                    if (it.position != -1) {
                        messagesListState.scrollToItem(it.position)
                    }
                }
                is MessageScreenContract.Effect.ShowUserProfile -> {

                }
            }
        }
    }
}