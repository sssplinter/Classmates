package presentation.screens.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import navigation.Screen
import navigation.component.NavHostController
import org.kodein.di.compose.rememberInstance
import presentation.components.LoadingDialog
import presentation.components.NoInternetDialog
import presentation.components.NoSuchAccountDialog
import util.resetActivationState

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel: LoginScreenViewModel by rememberInstance()

    val showNoInternetConnectionDialog = remember { mutableStateOf(false) }
    val showLoadingDialog = remember { mutableStateOf(false) }
    val showNoSuchAccountDialog = remember { mutableStateOf(false) }

    initObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        navController = navController,
        showNoInternetConnectionDialog = showNoInternetConnectionDialog,
        showLoadingDialog = showLoadingDialog,
        showNoSuchAccountDialog = showNoSuchAccountDialog)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize().blur(radius = 5.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource("background.png"),
            contentDescription = "",
            colorFilter = ColorFilter.tint(
                color = Color(
                    alpha = if (isSystemInDarkTheme()) 0.5f else 0.2f,
                    red = 0f,
                    green = 0f,
                    blue = 0f
                ),
                blendMode = BlendMode.SrcOver
            )
        )

        LogBox(
            viewModel = viewModel,
            onLogin = { email, password ->
                viewModel.setEvent(LoginScreenContract.Event.OnSignClick(email, password))
            }
        )

        if (showLoadingDialog.value) {
            LoadingDialog(
                backgroundColor = Color.White,
                shape = MaterialTheme.shapes.medium
            )
        }

        if (showNoInternetConnectionDialog.value) {
            NoInternetDialog(
                iconSrc = "no_wifi.png",
                message = "No internet connection",
                actionMessage = "Press to close",
                backgroundColor = Color.White,
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    viewModel.setEvent(LoginScreenContract.Event.OnNoWiFiBtnClick)
                }
            )
        }

        if (showNoSuchAccountDialog.value) {
            NoSuchAccountDialog(
                message = "User isn't exist",
                buttonText = "Ok",
                backgroundColor = Color.White,
                shape = MaterialTheme.shapes.medium,
                onOkClick = {
                    viewModel.setEvent(LoginScreenContract.Event.OnNoAccountOkBtnClick)
                }
            )
        }
    }
}

private fun initObservable(
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