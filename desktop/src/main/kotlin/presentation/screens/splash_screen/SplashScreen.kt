package presentation.screens.splash_screen

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import navigation.component.NavHostController
import ui.theme.MEDIUM_PADDING

@Composable
fun SplashScreen(navController: NavHostController) {
    var startLogoAnimation by remember { mutableStateOf(false) }
    val alphaLogoAnim = animateFloatAsState(targetValue = if (startLogoAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000))
    var startTextAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        startLogoAnimation = true
        delay(1000)
        startTextAnimation = true
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
                text = if (startTextAnimation) "Classmates" else "",
                color = Color.White,
                fontSize = 120.sp
            )
        }
    }
}
