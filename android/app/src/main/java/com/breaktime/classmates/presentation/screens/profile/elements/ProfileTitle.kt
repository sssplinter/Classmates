package com.breaktime.classmates.presentation.screens.profile.elements

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ProfileTitle(text: String) {
    Text(
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        text = text,
    )
}