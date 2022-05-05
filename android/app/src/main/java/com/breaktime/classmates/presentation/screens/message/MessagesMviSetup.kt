package com.breaktime.classmates.presentation.screens.message

import androidx.compose.runtime.MutableState
import com.breaktime.classmates.util.resetActivationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun initChatsObservable(
    scope: CoroutineScope,
    viewModel: MessagesScreenViewModel,
    showLoadingDialog: MutableState<Boolean>,
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
}