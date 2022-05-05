package com.breaktime.classmates.presentation.screens.settings

import com.breaktime.classmates.domain.entities.data.Languages
import com.breaktime.classmates.domain.entities.data.Themes
import com.breaktime.classmates.presentation.base.UiEffect
import com.breaktime.classmates.presentation.base.UiEvent
import com.breaktime.classmates.presentation.base.UiState

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