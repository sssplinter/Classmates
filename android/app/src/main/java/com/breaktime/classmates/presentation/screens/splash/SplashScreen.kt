package com.breaktime.classmates.presentation.screens.splash

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.breaktime.classmates.R
import com.breaktime.classmates.localization.Vocabulary
import com.breaktime.classmates.ui.theme.MEDIUM_PADDING
import kotlinx.coroutines.delay
import org.kodein.di.compose.rememberInstance

@Composable
fun SplashScreen(navController: NavHostController) {
    val viewModel: SplashScreenViewModel by rememberInstance()

    var startLogoAnimation by remember { mutableStateOf(false) }
    val alphaLogoAnim = animateFloatAsState(
        targetValue = if (startLogoAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )
    var startTextAnimation by remember { mutableStateOf(false) }

    initSplashObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        navController = navController
    )

    LaunchedEffect(true) {
        viewModel.setEvent(SplashScreenContract.Event.OnAnimationStart)
        startLogoAnimation = true
        delay(1000)
        startTextAnimation = true
        delay(1500)
        viewModel.setEvent(SplashScreenContract.Event.OnAnimationEnd)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Image(
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 5.dp),
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha = alphaLogoAnim.value),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(MEDIUM_PADDING)
                    .height(150.dp),
                painter = painterResource(R.drawable.owl),
                contentDescription = Vocabulary.localization.logoImgContent,
                tint = Color.White
            )
            Text(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = LinearOutSlowInEasing
                        )
                    ),
                text = if (startTextAnimation) Vocabulary.localization.appName else Vocabulary.localization.emptyText,
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
