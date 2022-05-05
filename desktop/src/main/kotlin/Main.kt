import androidx.compose.ui.Alignment
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import di.kodein
import domain.entities.data.Languages
import domain.entities.data.Themes
import domain.entities.data.toAppTheme
import domain.use_cases.settings.GetLanguageUseCase
import domain.use_cases.settings.GetThemeUseCase
import localization.Language
import localization.Localization
import localization.Vocabulary
import localization.languages.LanguageEn
import org.kodein.di.compose.withDI
import org.kodein.di.instance
import presentation.screens.host_screen.HostScreen
import ui.theme.AppTheme
import java.awt.Dimension

private val DEFAULT_WIDTH = 900.dp
private val DEFAULT_HEIGHT = 600.dp

private val MIN_WIDTH = 500.dp
private val MIN_HEIGHT = 500.dp

fun main() = application {
    withDI(kodein) {
        Window(
            title = Vocabulary.localization.appName,//TODO
            state = WindowState(
                width = DEFAULT_WIDTH,
                height = DEFAULT_HEIGHT,
                position = WindowPosition(Alignment.Center)
            ),
            onCloseRequest = ::exitApplication
        ) {
            window.setMinSize(
                width = MIN_WIDTH,
                height = MIN_HEIGHT
            )

            AppTheme(getTheme()) {
                Localization(getLocalization()) {
                    HostScreen()
                }
            }
        }
    }
}

private fun getTheme(): AppTheme {
    val getThemeUseCase: GetThemeUseCase by kodein.di.instance()
    return getThemeUseCase()?.let { theme -> Themes.valueOf(theme).toAppTheme() } ?: AppTheme.AUTO
}

private fun getLocalization(): Language {
    val getLanguageUseCase: GetLanguageUseCase by kodein.di.instance()
    return getLanguageUseCase()?.let { language -> Languages.valueOf(language).language } ?: LanguageEn
}

private fun ComposeWindow.setMinSize(width: Dp, height: Dp) {
    val size = Dimension()
    size.width = width.value.toInt()
    size.height = height.value.toInt()
    this.minimumSize = size
}
