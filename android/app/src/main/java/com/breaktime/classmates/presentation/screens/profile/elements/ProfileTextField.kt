package com.breaktime.classmates.presentation.screens.profile.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING

@Composable
fun ProfileTextField(text: MutableState<String>) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = EXTRA_SMALL_PADDING, vertical = 3.dp),
        textStyle = TextStyle(fontSize = 16.sp),
        singleLine = true,
        value = text.value,
        onValueChange = { text.value = it }
    )
}