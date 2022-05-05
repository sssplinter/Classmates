package presentation.screens.main_screen

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import navigation.component.NavHostController

fun initMainObservable(
    scope: CoroutineScope,
    viewModel: MainScreenViewModel,
    tabNavController: NavHostController,
    isProfileDialog: MutableState<Boolean>,
    currentTabRoute: MutableState<String>,
    isMessagesNotified: MutableState<Boolean>,
    isConnectionsNotified: MutableState<Boolean>,
) {
    scope.launch {
        viewModel.effect.collect {
            scope.ensureActive()
            when (it) {
                is MainScreenContract.Effect.ChangeFragment -> {
                    tabNavController.navigate(it.route)
                    currentTabRoute.value = it.route
                }
                MainScreenContract.Effect.OpenProfile -> isProfileDialog.value = true
                MainScreenContract.Effect.CloseProfile -> isProfileDialog.value = false
                MainScreenContract.Effect.NotifyMessages -> isMessagesNotified.value = true
                MainScreenContract.Effect.NotifyConnections -> isConnectionsNotified.value = true
            }
        }
    }
}