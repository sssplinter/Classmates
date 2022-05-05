package com.breaktime.classmates.presentation.screens.profile.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING

@Composable
inline fun ProfileColumnContainer(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .padding(EXTRA_SMALL_PADDING),
        content = content
    )
}

@Composable
inline fun ProfileRowContainer(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .padding(EXTRA_SMALL_PADDING),
    ) { content() }
}