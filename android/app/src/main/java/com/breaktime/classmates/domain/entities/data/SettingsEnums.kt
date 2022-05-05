package com.breaktime.classmates.domain.entities.data

import com.breaktime.classmates.localization.Language
import com.breaktime.classmates.localization.languages.LanguageEn
import com.breaktime.classmates.localization.languages.LanguageRu
import com.breaktime.classmates.ui.theme.AppTheme

enum class Themes(val imgSrc: String) {
    DAY("day_mode.png"),
    NIGHT("night_mode.png"),
    DAY_NIGHT("day_night_mode.png")
}

enum class Languages(val imgSrc: String, val language: Language) {
    EN("flags/uk_flag.svg", LanguageEn),
    FR("flags/french_flag.svg", LanguageEn),
    RU("flags/ru_flag.svg", LanguageRu)
}

// TODO: uncomment

fun Themes.toAppTheme() = when(this) {
    Themes.DAY -> AppTheme.DAY
    Themes.NIGHT -> AppTheme.NIGHT
    Themes.DAY_NIGHT -> AppTheme.AUTO
}