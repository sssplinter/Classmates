package com.breaktime.classmates.presentation.screens.profile.elements

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING
import com.breaktime.classmates.ui.theme.ProfileOutline

@Composable
fun ProfileImageButton(@DrawableRes image: Int, onClick: () -> Unit) {
    TextButton(
        modifier = Modifier
            .padding(end = EXTRA_SMALL_PADDING)
            .size(70.dp),
        shape = RoundedCornerShape(40.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = MaterialTheme.colors.ProfileOutline
        ),
        onClick = onClick
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = ""
        )
    }
}