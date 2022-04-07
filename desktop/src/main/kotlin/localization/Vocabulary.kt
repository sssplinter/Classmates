package localization

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import localization.languages.LanguageEn
import localization.languages.LanguageRu
import ui.theme.AppTheme

private var onChangeLanguage: ((Language) -> Unit)? = null
private val LocalLocalization = compositionLocalOf { defaultLocalization }
private val defaultLocalization: Language = LanguageEn

object Vocabulary {
    val localization: Language
        @Composable
        @get:ReadOnlyComposable
        get() = LocalLocalization.current
}

fun rememberLocalization(language: Language) {
    onChangeLanguage?.invoke(language)
}

@Composable
fun Localization(content: @Composable () -> Unit) {
    var locale by remember { mutableStateOf(defaultLocalization) }
    if (onChangeLanguage == null) {
        onChangeLanguage = { language ->
            locale = language
        }
    }

    CompositionLocalProvider(
        values = arrayOf(LocalLocalization provides locale),
        content = content
    )
}