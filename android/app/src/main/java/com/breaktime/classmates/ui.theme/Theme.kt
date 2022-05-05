package com.breaktime.classmates.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import com.breaktime.classmatei.theme.Shapes
import com.breaktime.classmatei.theme.Typography

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

private var currentTheme = AppTheme.AUTO
private var themeAppListener: ((AppTheme) -> Unit)? = null

@Composable
fun AppTheme(startTheme: AppTheme, content: @Composable () -> Unit) {
    val systemTheme: Boolean = isSystemInDarkTheme()
    var darkTheme by remember {
        mutableStateOf(
            when (startTheme) {
                AppTheme.DAY -> false
                AppTheme.NIGHT -> true
                AppTheme.AUTO -> systemTheme
            }
        )
    }
    themeAppListener = {
        darkTheme = when (currentTheme) {
            AppTheme.DAY -> false
            AppTheme.NIGHT -> true
            AppTheme.AUTO -> systemTheme
        }
    }

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
}

enum class AppTheme { DAY, NIGHT, AUTO }
