package presentation.components.dialogs

import androidx.compose.runtime.Composable
import localization.Vocabulary

@Composable
fun NoConnectionDialog() {
    Popup {
        BasicDialogBody(
            title = Vocabulary.localization.noInternet,
            message = Vocabulary.localization.noInternetMsg,
            imgSrc = "no_wifi.png"
        )
    }
}