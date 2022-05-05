package com.breaktime.classmates.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.breaktime.classmates.R
import org.kodein.di.compose.rememberInstance
import com.breaktime.classmates.presentation.components.dialogs.LoadingDialog
import com.breaktime.classmates.presentation.screens.login.elements.LogBox
import com.breaktime.classmates.presentation.screens.login.elements.UserInfoBox

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel: LoginScreenViewModel by rememberInstance()

    val showLoginDialog = remember { mutableStateOf(false) }
    val showDataConfirmationDialog = remember { mutableStateOf(false) }
    val showLoadingDialog = remember { mutableStateOf(false) }

    initLoginObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        navController = navController,
        showLoginDialog = showLoginDialog,
        showDataConfirmationDialog = showDataConfirmationDialog,
        showLoadingDialog = showLoadingDialog,
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize().blur(radius = 5.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.background),
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

        if (showLoginDialog.value) {
            LogBox(
                viewModel = viewModel,
                onLogin = { email, password ->
                    viewModel.setEvent(LoginScreenContract.Event.OnSignClick(email, password))
                }
            )
        }

        if (showDataConfirmationDialog.value) {
            UserInfoBox(
                onConfirmClick = { name, surname ->
                    viewModel.setEvent(LoginScreenContract.Event.OnConfirmUserDataBtnClick(name, surname))
                },
                onBackClick = {
                    viewModel.setEvent(LoginScreenContract.Event.OnBackToLoginBtnClick)
                }
            )
        }

        if (showLoadingDialog.value) {
            LoadingDialog()
        }
    }
}