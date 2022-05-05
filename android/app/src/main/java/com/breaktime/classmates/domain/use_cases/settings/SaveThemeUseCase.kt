package com.breaktime.classmates.domain.use_cases.settings

import com.breaktime.classmates.domain.source.settings.SettingsRepository

class SaveThemeUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(theme: String) = settingsRepository.saveTheme(theme)
}