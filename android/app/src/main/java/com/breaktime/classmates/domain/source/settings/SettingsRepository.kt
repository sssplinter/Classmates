package com.breaktime.classmates.domain.source.settings

import com.breaktime.classmates.domain.source.settings.local.SettingsStorage

class SettingsRepository(
    private val settingsStorage: SettingsStorage,
) {
    fun saveTheme(theme: String) {
        settingsStorage.saveTheme(theme)
    }

    fun getTheme() = settingsStorage.getTheme()

    fun saveLanguage(language: String) {
        settingsStorage.saveLanguage(language)
    }

    fun getLanguage() = settingsStorage.getLanguage()
}