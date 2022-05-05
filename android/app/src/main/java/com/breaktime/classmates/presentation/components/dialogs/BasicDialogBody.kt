package com.breaktime.classmates.presentation.components.dialogs

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.breaktime.classmates.presentation.components.RoundedTextField

@Composable
fun Popup(onOutBoxClick: (() -> Unit)? = null, content: @Composable (BoxScope.() -> Unit)) {
    Popup(
        popupPositionProvider = object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset = IntOffset.Zero
        },
        properties = PopupProperties(focusable = true)
    ) {
        ShadowBox(onOutBoxClick) { content() }
    }
}

@Composable
fun BoxScope.BasicDialogBody(
    title: String,
    message: String,
    imgSrc: Int,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .size(250.dp)
            .animateContentSize()
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
            .clickable(enabled = onClick != null, onClick = onClick ?: {})
            .padding(16.dp)
            .align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.height(130.dp),
            contentScale = ContentScale.Fit,
            painter = painterResource(imgSrc),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            fontSize = 14.sp
        )
    }
}