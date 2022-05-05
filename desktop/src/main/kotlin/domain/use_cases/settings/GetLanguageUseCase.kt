package domain.use_cases.settings

import domain.source.settings.SettingsRepository

class GetLanguageUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.getLanguage()
}