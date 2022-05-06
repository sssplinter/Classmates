package domain.entities.data

import localization.Language
import localization.languages.LanguageEn
import localization.languages.LanguageFr
import localization.languages.LanguageRu
import ui.theme.AppTheme

enum class Themes(val imgSrc: String) {
    DAY("day_mode.png"),
    NIGHT("night_mode.png"),
    DAY_NIGHT("day_night_mode.png")
}

enum class Languages(val imgSrc: String, val language: Language) {
    EN("flags/uk_flag.svg", LanguageEn),
    FR("flags/french_flag.svg", LanguageFr),
    RU("flags/ru_flag.svg", LanguageRu)
}

fun Themes.toAppTheme() = when(this) {
    Themes.DAY -> AppTheme.DAY
    Themes.NIGHT -> AppTheme.NIGHT
    Themes.DAY_NIGHT -> AppTheme.AUTO
}