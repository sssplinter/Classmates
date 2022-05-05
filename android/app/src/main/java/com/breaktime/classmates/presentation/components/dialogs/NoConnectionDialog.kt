package com.breaktime.classmates.presentation.components.dialogs

import androidx.compose.runtime.Composable
import com.breaktime.classmates.R
import com.breaktime.classmates.localization.Vocabulary

@Composable
fun NoConnectionDialog() {
    Popup {
        BasicDialogBody(
            title = Vocabulary.localization.noInternet,
            message = Vocabulary.localization.noInternetMsg,
            imgSrc = R.drawable.no_wifi
        )
    }
}