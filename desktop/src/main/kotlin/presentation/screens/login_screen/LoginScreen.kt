package presentation.screens.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import presentation.components.LogBox

@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize().blur(radius = 5.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource("background.png"),
            contentDescription = "",
            colorFilter = if (isSystemInDarkTheme())
                ColorFilter.tint(
                    color = Color(
                        alpha = 0.5f,
                        red = 0f,
                        green = 0f,
                        blue = 0f),
                    blendMode = BlendMode.SrcOver) else null
        )
        LogBox()
    }
}