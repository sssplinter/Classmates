package domain.use_cases.settings

import domain.source.settings.SettingsRepository

class SaveThemeUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(theme: String) = settingsRepository.saveTheme(theme)
}