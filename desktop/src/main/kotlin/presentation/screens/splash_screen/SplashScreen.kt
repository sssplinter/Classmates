package presentation.screens.splash_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import localization.Vocabulary
import navigation.Screen
import navigation.component.NavHostController
import org.kodein.di.compose.rememberInstance
import presentation.components.LoadingDialog
import presentation.components.NoInternetDialog
import ui.theme.MEDIUM_PADDING

@Composable
fun SplashScreen(navController: NavHostController) {
    val viewModel: SplashScreenViewModel by rememberInstance()

    var startLogoAnimation by remember { mutableStateOf(false) }
    val alphaLogoAnim = animateFloatAsState(targetValue = if (startLogoAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000))
    var startTextAnimation by remember { mutableStateOf(false) }
    val showNoInternetConnectionDialog = remember { mutableStateOf(false) }
    val showLoadingDialog = remember { mutableStateOf(false) }

    initObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        navController = navController,
        showNoInternetConnectionDialog = showNoInternetConnectionDialog,
        showLoadingDialog = showLoadingDialog)

    LaunchedEffect(true) {
        viewModel.setEvent(SplashScreenContract.Event.OnAnimationStart)
        startLogoAnimation = true
        delay(1000)
        startTextAnimation = true
        delay(1500)
        viewModel.setEvent(SplashScreenContract.Event.OnAnimationEnd)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(modifier = Modifier.fillMaxSize().blur(radius = 5.dp),
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
        Row(modifier = Modifier.alpha(alpha = alphaLogoAnim.value),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.padding(MEDIUM_PADDING).height(150.dp),
                painter = painterResource("owl.svg"),
                contentDescription = "logo icon",
                tint = Color.White
            )
            Text(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = tween(durationMillis = 1000,
                            easing = LinearOutSlowInEasing)
                    ),
                text = if (startTextAnimation) Vocabulary.localization.appName else Vocabulary.localization.emptyText,
                color = Color.White,
                fontSize = 120.sp
            )
        }

        if (showNoInternetConnectionDialog.value) {
            NoInternetDialog(
                iconSrc = "no_wifi.png",
                message = "No internet connection",
                actionMessage = "Press to repeat",
                backgroundColor = Color.White,
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    viewModel.setEvent(SplashScreenContract.Event.OnRepeatAuthCheckClick)
                }
            )
        }

        if (showLoadingDialog.value) {
            LoadingDialog(
                backgroundColor = Color.White,
                shape = MaterialTheme.shapes.medium
            )
        }
    }
}

private fun initObservable(
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
                    navController.navigate(Screen.Home.route)
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
                    showNoInternetConnectionDialog.value = true
                    showLoadingDialog.value = false
                }
                is SplashScreenContract.SplashScreenState.Loading -> {
                    showNoInternetConnectionDialog.value = true
                    showLoadingDialog.value = true
                }
                is SplashScreenContract.SplashScreenState.Idle -> {
                    showNoInternetConnectionDialog.value = false
                    showLoadingDialog.value = false
                }
            }
        }
    }
}
