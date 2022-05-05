package com.breaktime.classmates.presentation.components.dialogs

import androidx.compose.runtime.Composable
import com.breaktime.classmates.R

@Composable
fun NoAuthorizationDialog(onClick: () -> Unit) {
    Popup {
        BasicDialogBody(
            title = "NoAuthorizationDialog",
            message = "",
            imgSrc = R.drawable.no_wifi,
            onClick = onClick
        )
    }
}