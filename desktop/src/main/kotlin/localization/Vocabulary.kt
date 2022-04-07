package localization

import androidx.compose.runtime.*
import localization.languages.LanguageEn

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