package com.breaktime.classmates.presentation.screens.profile.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING

@Composable
fun ProfileReadOnlyFiled(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = EXTRA_SMALL_PADDING, vertical = 3.dp),
        fontSize = 16.sp,
        color = Color.Gray
    )
}