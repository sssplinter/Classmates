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
import kotlinx.coroutines.delay
import navigation.Screen
import navigation.component.NavHostController
import presentation.components.LoadingDialog

@Composable
fun LoginScreen(navController: NavHostController) {
    var isLoading by remember { mutableStateOf(false) }

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
            onLogIn = { email, passoword ->
                isLoading = true
            },
            onSignUp = { email, passoword ->
                isLoading = true
            }
        )

        if (isLoading) {
            LoadingDialog(
                backgroundColor = Color.White,
                shape = MaterialTheme.shapes.medium
            )
            LaunchedEffect(true) {
                delay(2000)
                navController.navigate(Screen.Home.route)
            }
        }
    }
}