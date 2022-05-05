package com.breaktime.classmates.domain.entities.data

import com.breaktime.classmates.R
import com.breaktime.classmates.localization.Language
import com.breaktime.classmates.localization.languages.LanguageEn
import com.breaktime.classmates.localization.languages.LanguageFr
import com.breaktime.classmates.localization.languages.LanguageRu
import com.breaktime.classmates.ui.theme.AppTheme

enum class Themes(val imgSrc: Int) {
    DAY(R.drawable.day_mode),
    NIGHT(R.drawable.night_mode),
    AUTO(R.drawable.day_night_mode),
}

enum class Languages(val imgSrc: Int, val language: Language) {
    EN(R.drawable.ic_uk_flag, LanguageEn),
    FR(R.drawable.ic_fr_flag, LanguageFr),
    RU(R.drawable.ic_ru_flag, LanguageRu)
}

fun Themes.toAppTheme() = when (this) {
    Themes.DAY -> AppTheme.DAY
    Themes.NIGHT -> AppTheme.NIGHT
    Themes.AUTO -> AppTheme.AUTO
}