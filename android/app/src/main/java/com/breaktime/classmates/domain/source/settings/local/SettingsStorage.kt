package com.breaktime.classmates.domain.source.settings.local

interface SettingsStorage {
    fun saveTheme(theme: String)
    fun getTheme(): String?
    fun saveLanguage(language: String)
    fun getLanguage(): String?
}