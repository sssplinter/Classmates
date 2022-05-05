package com.breaktime.classmates.presentation.screens.host

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun initHostObservable(
    scope: CoroutineScope,
    viewModel: HostScreenViewModel,
    showBadInputDialog: MutableState<Boolean>,
    showForbiddenDialog: MutableState<Boolean>,
    showNoAuthorizationDialog: MutableState<Boolean>,
    showNoConnectionDialog: MutableState<Boolean>,
) {
    scope.launch {
        viewModel.effect.collect {
            scope.ensureActive()
            when (it) {
                HostScreenContract.Effect.ShowBadInputDialog -> showBadInputDialog.value = true
                HostScreenContract.Effect.ShowForbiddenDialog -> showForbiddenDialog.value = true
                HostScreenContract.Effect.ShowNoAuthorizationDialog -> showNoAuthorizationDialog.value = true
                HostScreenContract.Effect.ShowNoConnectionDialog -> showNoConnectionDialog.value = true
                HostScreenContract.Effect.HideNoConnectionDialog -> showNoConnectionDialog.value = false
            }
        }
    }
}