package presentation.screens.settings_screen

import androidx.compose.runtime.MutableState
import domain.entities.data.Languages
import domain.entities.data.Themes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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