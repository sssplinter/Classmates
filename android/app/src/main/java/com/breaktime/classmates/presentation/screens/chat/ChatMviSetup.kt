package com.breaktime.classmates.presentation.screens.chat

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import com.breaktime.classmates.presentation.screens.message.FindMessageStatus
import com.breaktime.classmates.util.resetActivationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun initChatObservable(
    scope: CoroutineScope,
    viewModel: ChatScreenViewModel,
    messagesListState: LazyListState,
    findMessageStatus: MutableState<FindMessageStatus>,
) {
    scope.launch {
        viewModel.effect.collect {
            scope.ensureActive()
            when (it) {
                is ChatScreenContract.Effect.ShowFoundMessage -> {
                    findMessageStatus.value = FindMessageStatus(it.position, it.hasNext, it.hasPrev)
                    if (it.position != -1) {
                        messagesListState.animateScrollToItem(it.position, -50)
                    }
                }
                is ChatScreenContract.Effect.UpdateChatData -> {
                    viewModel.currentMessages.clear()
                    viewModel.currentMessages.addAll(it.messagesList)
                    messagesListState.animateScrollToItem(it.messagesList.lastIndex)
                }
            }
        }
    }
}