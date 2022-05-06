package presentation.screens.settings_screen

import domain.entities.data.Languages
import domain.entities.data.Themes
import domain.entities.data.toAppTheme
import domain.use_cases.settings.GetLanguageUseCase
import domain.use_cases.settings.GetThemeUseCase
import domain.use_cases.settings.SaveLanguageUseCase
import domain.use_cases.settings.SaveThemeUseCase
import localization.rememberLocalization
import presentation.base.BaseViewModel
import ui.theme.rememberTheme

class SettingsScreenViewModel(
    private val getLanguageUseCase: GetLanguageUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val saveLanguageUseCase: SaveLanguageUseCase,
    private val saveThemeUseCase: SaveThemeUseCase,
) : BaseViewModel<SettingsScreenContract.Event, SettingsScreenContract.State, SettingsScreenContract.Effect>() {
    var savedTheme: Themes = Themes.DAY_NIGHT
    var savedLanguage: Languages = Languages.EN

    init {
        loadSavedTheme()
        loadSavedLanguage()
    }

    private fun loadSavedTheme() {
        savedTheme = getThemeUseCase()?.let { Themes.valueOf(it) } ?: savedTheme
        setEffect { SettingsScreenContract.Effect.ChangeTheme(savedTheme) }
    }

    private fun loadSavedLanguage() {
        savedLanguage = getLanguageUseCase()?.let { Languages.valueOf(it) } ?: savedLanguage
        setEffect { SettingsScreenContract.Effect.ChangeLanguage(savedLanguage) }
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
        savedTheme = theme
        saveThemeUseCase(theme.name)
        rememberTheme(theme.toAppTheme())
        setEffect { SettingsScreenContract.Effect.ChangeTheme(theme) }
    }

    private fun updateLanguage(language: Languages) {
        savedLanguage = language
        saveLanguageUseCase(language.name)
        rememberLocalization(language.language)
        setEffect { SettingsScreenContract.Effect.ChangeLanguage(language) }
    }

    override fun clearState() {
        setState { copy(state = SettingsScreenContract.SettingsScreenState.Idle) }
    }
}