package presentation.components.dialogs

import androidx.compose.runtime.Composable

@Composable
fun BadInputDialog(onClick: () -> Unit) {
    Popup {
        BasicDialogBody(
            title = "",
            message = "",
            imgSrc ="no_wifi.png",
            onClick = onClick
        )
    }
}