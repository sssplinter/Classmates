package presentation.screens.settings_screen

import domain.entities.data.Languages
import domain.entities.data.Themes
import presentation.base.UiEffect
import presentation.base.UiEvent
import presentation.base.UiState

class SettingsScreenContract {
    sealed class Event : UiEvent {
        data class OnThemeClick(val theme: Themes) : Event()
        data class OnLanguageClick(val language: Languages) : Event()
    }

    data class State(val state: SettingsScreenState) : UiState

    sealed class SettingsScreenState {
        object Idle : SettingsScreenState()
        object Loading : SettingsScreenState()
    }

    sealed class Effect : UiEffect {
        data class ChangeTheme(val theme: Themes) : Effect()
        data class ChangeLanguage(val language: Languages) : Effect()
    }
}