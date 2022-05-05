package ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import com.jthemedetecor.OsThemeDetector
import org.jetbrains.skiko.SystemTheme
import org.jetbrains.skiko.currentSystemTheme

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

enum class AppTheme { DAY, NIGHT, AUTO }

private var currentTheme = AppTheme.AUTO
private var themeAppListener: ((AppTheme) -> Unit)? = null
private var themeDesktopListener: ((AppTheme) -> Unit)? = null

@Composable
fun AppTheme(startTheme: AppTheme, content: @Composable () -> Unit) {
    val darkTheme = rememberDesktopDarkTheme(startTheme)

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

fun rememberTheme(theme: AppTheme) {
    currentTheme = theme
    themeAppListener?.invoke(theme)
    themeDesktopListener?.invoke(theme)
}

@Composable
private fun rememberDesktopDarkTheme(startTheme: AppTheme): Boolean {
    var darkTheme by remember {
        mutableStateOf(
            when (startTheme) {
                AppTheme.DAY -> false
                AppTheme.NIGHT -> true
                AppTheme.AUTO -> currentSystemTheme == SystemTheme.DARK
            }
        )
    }

    themeDesktopListener = {
        darkTheme = when (currentTheme) {
            AppTheme.DAY -> false
            AppTheme.NIGHT -> true
            AppTheme.AUTO -> currentSystemTheme == SystemTheme.DARK
        }
    }

    DisposableEffect(Unit) {
        val darkThemeListener: (Boolean) -> Unit = {
            darkTheme = when (currentTheme) {
                AppTheme.DAY -> false
                AppTheme.NIGHT -> true
                AppTheme.AUTO -> it
            }
        }

        val detector = OsThemeDetector.getDetector().apply {
            registerListener(darkThemeListener)
        }

        onDispose {
            detector.removeListener(darkThemeListener)
        }
    }

    return darkTheme
}