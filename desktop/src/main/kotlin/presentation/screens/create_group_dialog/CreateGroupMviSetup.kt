package presentation.screens.create_group_dialog

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import util.resetActivationState

fun initGroupCreationObservable(
    scope: CoroutineScope,
    viewModel: CreateGroupViewModel,
    friendsList: SnapshotStateList<CreateGroupViewModel.PersonItem>,
    showLoadingDialog: MutableState<Boolean>,
    closeDialog: () -> Unit,
) {
    scope.launch {
        viewModel.uiState.collect {
            scope.ensureActive()
            when (it.state) {
                is CreateGroupContract.CreateGroupScreenState.Loading -> {
                    resetActivationState(
                        activate = listOf(showLoadingDialog),
                    )
                }
                is CreateGroupContract.CreateGroupScreenState.Idle -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog)
                    )
                }
                is CreateGroupContract.CreateGroupScreenState.Created -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog)
                    )
                    closeDialog()
                }
            }
        }
    }
    scope.launch {
        viewModel.effect.collect {
            scope.ensureActive()
            when (it) {
                is CreateGroupContract.Effect.OnUsersLoaded -> {
                    friendsList.clear()
                    friendsList.addAll(it.users)
                }
            }
        }
    }
}