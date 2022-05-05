package com.breaktime.classmates.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

@Composable
fun WebImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeHolder: Painter,
) {
    Image(
        painter = placeHolder,
        modifier = modifier,
        contentScale = contentScale,
        contentDescription = null
    )
}
