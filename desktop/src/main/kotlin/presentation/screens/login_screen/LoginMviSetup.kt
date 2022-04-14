package presentation.screens.login_screen

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import navigation.Screen
import navigation.component.NavHostController
import util.resetActivationState


fun initLoginObservable(
    scope: CoroutineScope,
    viewModel: LoginScreenViewModel,
    navController: NavHostController,
    showNoInternetConnectionDialog: MutableState<Boolean>,
    showLoadingDialog: MutableState<Boolean>,
    showNoSuchAccountDialog: MutableState<Boolean>,
) {
    scope.launch {
        viewModel.uiState.collect {
            scope.ensureActive()
            when (it.loginScreenState) {
                is LoginScreenContract.LoginScreenState.NoSuchAccount -> {
                    resetActivationState(
                        activate = listOf(showNoSuchAccountDialog),
                        disActivate = listOf(showLoadingDialog, showNoInternetConnectionDialog)
                    )
                }
                is LoginScreenContract.LoginScreenState.NoInternetConnection -> {
                    resetActivationState(
                        activate = listOf(showNoInternetConnectionDialog),
                        disActivate = listOf(showLoadingDialog, showNoSuchAccountDialog)
                    )
                }
                is LoginScreenContract.LoginScreenState.Loading -> {
                    resetActivationState(
                        activate = listOf(showLoadingDialog),
                        disActivate = listOf(showNoInternetConnectionDialog,
                            showNoSuchAccountDialog)
                    )
                }
                is LoginScreenContract.LoginScreenState.Idle -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog,
                            showNoInternetConnectionDialog,
                            showNoSuchAccountDialog)
                    )
                }
                is LoginScreenContract.LoginScreenState.Authorized -> {
                    navController.popBackStack()
                    navController.navigate(Screen.Main.route)
                    viewModel.clearState()
                    scope.cancel()
                }
            }
        }
    }
}