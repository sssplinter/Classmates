package com.breaktime.classmates.presentation.screens.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.breaktime.classmates.navigation.GlobalNavGraph
import com.breaktime.classmates.navigation.Screen
import com.breaktime.classmates.presentation.components.dialogs.BadInputDialog
import com.breaktime.classmates.presentation.components.dialogs.ForbiddenDialog
import com.breaktime.classmates.presentation.components.dialogs.NoAuthorizationDialog
import com.breaktime.classmates.presentation.components.dialogs.NoConnectionDialog
import org.kodein.di.compose.rememberInstance

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
    GlobalNavGraph(screenController)

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
                screenController.backQueue.clear()
                screenController.navigate(Screen.Login.route)
            }
        )
    }

    if (showNoConnectionDialog.value) {
        NoConnectionDialog()
    }
}