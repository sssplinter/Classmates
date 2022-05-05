package com.breaktime.classmates.presentation.screens.profile.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.breaktime.classmates.ui.theme.SMALL_PADDING
import com.breaktime.classmates.ui.theme.THE_SMALLEST_PADDING
import com.breaktime.classmates.util.dp

@Composable
fun ProfileCommentText(
    text: String,
    fontSize: TextUnit,
    imageVector: ImageVector? = null,
    onImageClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SMALL_PADDING, vertical = THE_SMALLEST_PADDING),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            fontSize = fontSize,
            text = text
        )
        imageVector?.let {
            Icon(
                modifier = Modifier.size(14.sp.dp).clickable { onImageClick?.invoke() },
                imageVector = imageVector,
                contentDescription = "",
                tint = Color.Black
            )
        }
    }
}