package com.breaktime.classmates.presentation.components.dialogs

import androidx.compose.runtime.Composable
import com.breaktime.classmates.R

@Composable
fun NoConnectionDialog() {
    Popup {
        BasicDialogBody(
            title = "No internet connection",
            message = "Please reconnect to network",
            imgSrc = R.drawable.no_wifi
        )
    }
}