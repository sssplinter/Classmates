package com.breaktime.classmates.domain.use_cases.settings

import com.breaktime.classmates.domain.source.settings.SettingsRepository

class GetThemeUseCase (
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.getTheme()
}