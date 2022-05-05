package com.breaktime.classmates.presentation.components.dialogs

import androidx.compose.runtime.Composable
import com.breaktime.classmates.R
import com.breaktime.classmates.presentation.components.dialogs.BasicDialogBody
import com.breaktime.classmates.presentation.components.dialogs.Popup

@Composable
fun BadInputDialog(onClick: () -> Unit) {
    Popup {
        BasicDialogBody(
            title = "BadInputDialog",
            message = "",
            imgSrc = R.drawable.no_wifi,
            onClick = onClick
        )
    }
}