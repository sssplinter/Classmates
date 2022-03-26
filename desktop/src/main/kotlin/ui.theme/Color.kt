package ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightDray = Color(0xFFFAFAFA)
val DarkDray = Color(0xFF2A2A2A)

val LightButtonSelection = Color(0xFFE4D8F9)
val DarkButtonSelection = Color(0xFF474747)

val Colors.loginBackground
    @Composable
    get() = if (isLight) LightDray else DarkDray

val Colors.loginActiveButton
    @Composable
    get() = if (isLight) LightButtonSelection else DarkButtonSelection
