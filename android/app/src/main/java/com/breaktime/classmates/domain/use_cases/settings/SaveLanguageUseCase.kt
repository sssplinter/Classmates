package com.breaktime.classmates.domain.use_cases.settings

import com.breaktime.classmates.domain.source.settings.SettingsRepository

class SaveLanguageUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(language: String) = settingsRepository.saveLanguage(language)
}