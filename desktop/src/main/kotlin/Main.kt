import androidx.compose.ui.Alignment
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import presentation.screens.home_screen.HomeScreen
import ui.theme.AppTheme
import java.awt.Dimension

private val DEFAULT_WIDTH = 900.dp
private val DEFAULT_HEIGHT = 600.dp

private val MIN_WIDTH = 500.dp
private val MIN_HEIGHT = 500.dp

fun main() = application {
    Window(
        title = "Classmates",
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
        AppTheme {
            HomeScreen()
        }
    }
}

private fun ComposeWindow.setMinSize(width: Dp, height: Dp) {
    val size = Dimension()
    size.width = width.value.toInt()
    size.height = height.value.toInt()
    this.minimumSize = size
}
