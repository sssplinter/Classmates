package presentation.components.dialogs

import androidx.compose.runtime.Composable

@Composable
fun NoAuthorizationDialog(onClick: () -> Unit) {
    ShadowBox {
        BasicDialogBody(
            title = "",
            message = "",
            imgSrc ="no_wifi.png",
            onClick = onClick
        )
    }
}