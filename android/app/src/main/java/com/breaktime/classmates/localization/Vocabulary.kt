package com.breaktime.classmates.localization

import androidx.compose.runtime.*
import com.breaktime.classmates.localization.languages.LanguageEn

//private var onChangeLanguage: ((Language) -> Unit)? = null
//private val LocalLocalization = compositionLocalOf { LanguageEn }
//private val defaultLocalization: Language = LanguageEn

//object Vocabulary {
//    val localization: Language
//        @Composable
//        get() = LanguageEn
//        get() = LocalLocalization.current
//}

//fun rememberLocalization(language: Language) {
//    onChangeLanguage?.invoke(language)
//}

//@Composable
//fun Localization(startLanguage: Language, content: @Composable () -> Unit) {
//    var locale by remember { mutableStateOf(startLanguage) }
//    if (onChangeLanguage == null) {
//        onChangeLanguage = { language ->
//            locale = language
//        }
//    }

//    CompositionLocalProvider(
//        values = arrayOf(LocalLocalization provides locale),
//        content = content
//    )
//}





import androidx.compose.runtime.*

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
fun Localization(startLanguage: Language, content: @Composable () -> Unit) {
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