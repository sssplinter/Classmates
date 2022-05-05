package com.breaktime.classmates.presentation.components.dialogs

import androidx.compose.runtime.Composable
import com.breaktime.classmates.R

@Composable
fun ForbiddenDialog(onClick: () -> Unit) {
    Popup {
        BasicDialogBody(
            title = "ForbiddenDialog",
            message = "",
            imgSrc = R.drawable.no_wifi,
            onClick = onClick
        )
    }
}