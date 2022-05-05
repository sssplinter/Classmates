package com.breaktime.classmates.presentation.screens.profile.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileTitleTextButton(modifier: Modifier = Modifier, text: String, textColor: Color, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(30.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            fontSize = 18.sp,
            text = text,
            color = textColor
        )
    }
}