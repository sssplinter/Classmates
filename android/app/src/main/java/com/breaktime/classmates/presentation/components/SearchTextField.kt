package com.breaktime.classmates.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    onValueChanged: (String) -> Unit = {},
    hint: String = "",
    fontSize: TextUnit = TextUnit.Unspecified,
    hintFontSize: TextUnit = TextUnit.Unspecified,
    borderColor: Color = Color.Black,
    backgroundColor: Color = Color.Transparent,
) {
    BasicTextField(
        modifier = modifier,
        value = text.value,
        onValueChange = {
            text.value = it
            onValueChanged(it)
        },
        singleLine = true,
        textStyle = TextStyle(fontSize = fontSize)
    ) { innerTextField ->
        Row(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = MaterialTheme.shapes.medium
                )
                .clip(MaterialTheme.shapes.medium)
                .background(backgroundColor)
                .padding(EXTRA_SMALL_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(end = EXTRA_SMALL_PADDING),
                imageVector = Icons.Default.Search,
                contentDescription = ""
            )
            Box {
                if (text.value.isEmpty()) {
                    Text(text = hint, fontSize = hintFontSize)
                }
                innerTextField()
            }
        }
    }
}