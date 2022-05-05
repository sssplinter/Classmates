package com.breaktime.classmates.presentation.screens.settings

import com.breaktime.classmates.domain.entities.data.Languages
import com.breaktime.classmates.domain.entities.data.Themes
import com.breaktime.classmates.domain.entities.data.toAppTheme
import com.breaktime.classmates.domain.use_cases.settings.GetLanguageUseCase
import com.breaktime.classmates.domain.use_cases.settings.GetThemeUseCase
import com.breaktime.classmates.domain.use_cases.settings.SaveLanguageUseCase
import com.breaktime.classmates.domain.use_cases.settings.SaveThemeUseCase
import com.breaktime.classmates.localization.rememberLocalization
import com.breaktime.classmates.presentation.base.BaseViewModel
import com.breaktime.classmates.ui.theme.rememberTheme

//import com.breaktime.classmates.ui.theme.rememberTheme

class SettingsScreenViewModel(
    private val getLanguageUseCase: GetLanguageUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val saveLanguageUseCase: SaveLanguageUseCase,
    private val saveThemeUseCase: SaveThemeUseCase,
) : BaseViewModel<SettingsScreenContract.Event, SettingsScreenContract.State, SettingsScreenContract.Effect>() {

    init {
        loadSavedTheme()
        loadSavedLanguage()
    }

    private fun loadSavedTheme() {
        val savedTheme = getThemeUseCase()
        savedTheme?.let {
            setEffect { SettingsScreenContract.Effect.ChangeTheme(Themes.valueOf(savedTheme)) }
        }
    }

    private fun loadSavedLanguage() {
        val savedLanguage = getLanguageUseCase()
        savedLanguage?.let {
            setEffect { SettingsScreenContract.Effect.ChangeLanguage(Languages.valueOf(savedLanguage)) }
        }
    }

    override fun createInitialState(): SettingsScreenContract.State {
        return SettingsScreenContract.State(SettingsScreenContract.SettingsScreenState.Idle)
    }

    override fun handleEvent(event: SettingsScreenContract.Event) {
        when (event) {
            is SettingsScreenContract.Event.OnThemeClick -> updateTheme(event.theme)
            is SettingsScreenContract.Event.OnLanguageClick -> updateLanguage(event.language)
        }
    }

    private fun updateTheme(theme: Themes) {
        saveThemeUseCase(theme.name)
        rememberTheme(theme.toAppTheme())
        setEffect { SettingsScreenContract.Effect.ChangeTheme(theme) }
    }

    private fun updateLanguage(language: Languages) {
        saveLanguageUseCase(language.name)
        rememberLocalization(language.language)
        setEffect { SettingsScreenContract.Effect.ChangeLanguage(language) }
    }

    override fun clearState() {
        setState { copy(state = SettingsScreenContract.SettingsScreenState.Idle) }
    }
}