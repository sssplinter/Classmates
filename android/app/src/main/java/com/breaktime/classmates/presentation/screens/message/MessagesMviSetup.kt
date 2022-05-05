package com.breaktime.classmates.presentation.screens.message

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import com.breaktime.classmates.util.resetActivationState
import kotlinx.coroutines.flow.collect

fun initChatsObservable(
    scope: CoroutineScope,
    viewModel: MessagesScreenViewModel,
    messagesListState: LazyListState,
    showLoadingDialog: MutableState<Boolean>,
    findMessageStatus: MutableState<FindMessageStatus>,
) {
    scope.launch {
        viewModel.uiState.collect {
            scope.ensureActive()
            when (it.state) {
                is MessagesScreenContract.DialogsScreenState.Loading -> {
                    resetActivationState(
                        activate = listOf(showLoadingDialog),
                    )
                }
                is MessagesScreenContract.DialogsScreenState.Idle -> {
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
                is MessagesScreenContract.Effect.ShowFoundMessage -> {
                    findMessageStatus.value = FindMessageStatus(it.position, it.hasNext, it.hasPrev)
                    if (it.position != -1) {
                        messagesListState.animateScrollToItem(it.position, -50)
                    }
                }
                is MessagesScreenContract.Effect.ShowUserProfile -> {

                }
                is MessagesScreenContract.Effect.UpdateChatData -> {
                    viewModel.currentChatData.value = Triple(it.currentChatId, it.chatInfo, it.messagesList)
                    messagesListState.animateScrollToItem(it.messagesList.lastIndex)
                }
            }
        }
    }
}