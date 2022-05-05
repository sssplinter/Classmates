package presentation.components.dialogs

import androidx.compose.runtime.Composable

@Composable
fun ForbiddenDialog(onClick: () -> Unit) {
    Popup {
        BasicDialogBody(
            title = "",
            message = "",
            imgSrc ="no_wifi.png",
            onClick = onClick
        )
    }
}