package presentation.screens.profile_dialog

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import navigation.Screen
import navigation.component.NavHostController
import util.resetActivationState

fun initProfileObservable(
    scope: CoroutineScope,
    viewModel: ProfileScreenViewModel,
    navController: NavHostController,
    showLoadingDialog: MutableState<Boolean>,
) {
    scope.launch {
        viewModel.uiState.collect {
            scope.ensureActive()
            when (it.state) {
                is ProfileScreenContract.ProfileScreenState.Saved -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog),
                    )
                }
                is ProfileScreenContract.ProfileScreenState.Idle -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog)
                    )
                }
                is ProfileScreenContract.ProfileScreenState.Loading -> {
                    resetActivationState(
                        activate = listOf(showLoadingDialog),
                    )
                }
                is ProfileScreenContract.ProfileScreenState.LoggedOut,
                -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog)
                    )
                    navController.clearBackStack()
                    navController.navigate(Screen.Login.route)
                    viewModel.clearState()
                    scope.cancel()
                }
            }
        }
    }
    scope.launch {
        viewModel.effect.collect {
            scope.ensureActive()
            when (it) {
                is ProfileScreenContract.Effect.DataLoaded -> {
                    // TODO: 14.04.22 change MutableState for profile data
                }
            }
        }
    }
}