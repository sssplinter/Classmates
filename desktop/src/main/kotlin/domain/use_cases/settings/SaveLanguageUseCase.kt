package domain.use_cases.settings

import domain.source.settings.SettingsRepository

class SaveLanguageUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(language: String) = settingsRepository.saveLanguage(language)
}