package com.breaktime.classmates.domain.use_cases.settings

import com.breaktime.classmates.domain.source.settings.SettingsRepository

class GetLanguageUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.getLanguage()
}