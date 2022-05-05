package com.breaktime.classmates.domain.source.settings.local

import java.util.prefs.Preferences

private const val THEME = "theme"
private const val LANGUAGE = "language"

class SettingsStorageImpl : SettingsStorage {
    private val preferences = Preferences.userRoot().node(this::javaClass.name)

    override fun saveTheme(theme: String) = preferences.put(THEME, theme)

    override fun getTheme() = preferences.get(THEME, null)

    override fun saveLanguage(language: String) = preferences.put(LANGUAGE, language)

    override fun getLanguage() = preferences.get(LANGUAGE, null)
}