package presentation.screens.splash_screen

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import navigation.Screen
import navigation.component.NavHostController
import util.resetActivationState

fun initSplashObservable(
    scope: CoroutineScope,
    viewModel: SplashScreenViewModel,
    navController: NavHostController,
    showNoInternetConnectionDialog: MutableState<Boolean>,
    showLoadingDialog: MutableState<Boolean>,
) {
    scope.launch {
        viewModel.uiState.collect {
            scope.ensureActive()
            when (it.splashScreenState) {
                is SplashScreenContract.SplashScreenState.Authorized -> {
                    navController.popBackStack()
                    navController.navigate(Screen.Main.route)
                    viewModel.clearState()
                    scope.cancel()
                }
                is SplashScreenContract.SplashScreenState.Unauthorized -> {
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                    viewModel.clearState()
                    scope.cancel()
                }
                is SplashScreenContract.SplashScreenState.NoInternetConnection -> {
                    resetActivationState(
                        activate = listOf(showNoInternetConnectionDialog),
                        disActivate = listOf(showLoadingDialog)
                    )
                }
                is SplashScreenContract.SplashScreenState.Loading -> {
                    resetActivationState(
                        activate = listOf(showNoInternetConnectionDialog, showLoadingDialog),
                    )
                }
                is SplashScreenContract.SplashScreenState.Idle -> {
                    resetActivationState(
                        disActivate = listOf(showNoInternetConnectionDialog, showLoadingDialog)
                    )
                }
            }
        }
    }
}