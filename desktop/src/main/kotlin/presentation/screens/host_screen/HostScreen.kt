package presentation.screens.host_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import navigation.EnteringNavGraph
import navigation.Screen
import navigation.component.rememberNavController
import org.kodein.di.compose.rememberInstance
import presentation.components.dialogs.BadInputDialog
import presentation.components.dialogs.ForbiddenDialog
import presentation.components.dialogs.NoAuthorizationDialog
import presentation.components.dialogs.NoConnectionDialog

@Composable
fun HostScreen() {
    val viewModel: HostScreenViewModel by rememberInstance()

    val screenController = rememberNavController()
    val showBadInputDialog = remember { mutableStateOf(false) }
    val showForbiddenDialog = remember { mutableStateOf(false) }
    val showNoAuthorizationDialog = remember { mutableStateOf(false) }
    val showNoConnectionDialog = remember { mutableStateOf(false) }

    initHostObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        showBadInputDialog = showBadInputDialog,
        showForbiddenDialog = showForbiddenDialog,
        showNoAuthorizationDialog = showNoAuthorizationDialog,
        showNoConnectionDialog = showNoConnectionDialog
    )
    EnteringNavGraph(screenController)

    if (showBadInputDialog.value) {
        BadInputDialog(
            onClick = {
                showBadInputDialog.value = false
            }
        )
    }

    if (showForbiddenDialog.value) {
        ForbiddenDialog(
            onClick = {
                showForbiddenDialog.value = false
            }
        )
    }

    if (showNoAuthorizationDialog.value) {
        NoAuthorizationDialog(
            onClick = {
                showNoAuthorizationDialog.value = false
                screenController.clearBackStack()
                screenController.navigate(Screen.Login.route)
            }
        )
    }

    if (showNoConnectionDialog.value) {
        NoConnectionDialog()
    }
}