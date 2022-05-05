package com.breaktime.classmates.domain.source.settings.local

import android.content.Context
import android.content.SharedPreferences

private const val SETTINGS_PREFERENCES = "SETTINGS_PREFERENCES"
private const val THEME = "THEME"
private const val LANGUAGE = "LANGUAGE"

class SettingsStorageImpl(context: Context) : SettingsStorage {
    private val sharedPref = context.getSharedPreferences(
        SETTINGS_PREFERENCES, Context.MODE_PRIVATE
    )

    override fun saveTheme(theme: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(THEME, theme)
        editor.apply()
    }

    override fun getTheme() = sharedPref.getString(THEME, null)

    override fun saveLanguage(language: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(LANGUAGE, language)
        editor.apply()
    }

    override fun getLanguage() = sharedPref.getString(LANGUAGE, null)
}