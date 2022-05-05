package com.breaktime.classmates.presentation.screens.settings

import androidx.compose.runtime.MutableState
import com.breaktime.classmates.domain.entities.data.Languages
import com.breaktime.classmates.domain.entities.data.Themes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

fun initSettingsObservable(
    scope: CoroutineScope,
    viewModel: SettingsScreenViewModel,
    currentTheme: MutableState<Themes>,
    currentLanguage: MutableState<Languages>,
) {
    scope.launch {
        viewModel.effect.collect {
            scope.ensureActive()
            when (it) {
                is SettingsScreenContract.Effect.ChangeTheme -> {
                    currentTheme.value = it.theme
                }
                is SettingsScreenContract.Effect.ChangeLanguage -> {
                    currentLanguage.value = it.language
                }
            }
        }
    }
}