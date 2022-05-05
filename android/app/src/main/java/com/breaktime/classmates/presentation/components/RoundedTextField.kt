package com.breaktime.classmates.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.breaktime.classmates.ui.theme.SMALL_PADDING

@Composable
fun RoundedTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    hint: String = "",
) {
    BasicTextField(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
            .padding(SMALL_PADDING)
            .height(20.dp),
        value = text.value,
        onValueChange = { text.value = it },
        singleLine = true
    ) { innerTextField ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box {
                if (text.value.isEmpty()) {
                    Text(text = hint)
                }
                innerTextField()
            }
        }
    }
}