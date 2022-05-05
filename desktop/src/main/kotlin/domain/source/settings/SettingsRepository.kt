package domain.source.settings

import domain.source.settings.local.SettingsStorage

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