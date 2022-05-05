package presentation.components.dialogs

import androidx.compose.runtime.Composable

@Composable
fun NoConnectionDialog() {
    Popup {
        BasicDialogBody(
            title = "No internet connection",
            message = "Please reconnect to network",
            imgSrc = "no_wifi.png"
        )
    }
}