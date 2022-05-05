package com.breaktime.classmates

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.breaktime.classmates.di.kodein
import com.breaktime.classmates.domain.entities.data.Languages
import com.breaktime.classmates.domain.entities.data.Themes
import com.breaktime.classmates.domain.entities.data.toAppTheme
import com.breaktime.classmates.domain.use_cases.settings.GetLanguageUseCase
import com.breaktime.classmates.domain.use_cases.settings.GetThemeUseCase
import com.breaktime.classmates.localization.Language
import com.breaktime.classmates.localization.Localization
import com.breaktime.classmates.localization.languages.LanguageEn
import com.breaktime.classmates.presentation.screens.host.HostScreen
import com.breaktime.classmates.ui.theme.AppTheme
import org.kodein.di.compose.withDI
import org.kodein.di.instance

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            withDI(kodein) {
                AppTheme(getCurrentTheme()) {
                    Localization(getLocalization()) {
                        HostScreen()
                    }
                }
            }
        }
    }
}

private fun getCurrentTheme(): AppTheme {
    val getThemeUseCase: GetThemeUseCase by kodein.di.instance()
    return getThemeUseCase()?.let { theme -> Themes.valueOf(theme).toAppTheme() } ?: AppTheme.AUTO
}

private fun getLocalization(): Language {
    val getLanguageUseCase: GetLanguageUseCase by kodein.di.instance()
    return getLanguageUseCase()?.let { language -> Languages.valueOf(language).language }
        ?: LanguageEn
}