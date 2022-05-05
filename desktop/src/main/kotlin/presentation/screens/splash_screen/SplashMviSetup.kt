package presentation.screens.splash_screen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import navigation.Screen
import navigation.component.NavHostController

fun initSplashObservable(
    scope: CoroutineScope,
    viewModel: SplashScreenViewModel,
    navController: NavHostController,
) {
    scope.launch {
        viewModel.uiState.collect {
            scope.ensureActive()
            when (it.state) {
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
                is SplashScreenContract.SplashScreenState.Idle -> {}
            }
        }
    }
}