package com.breaktime.classmates.presentation.screens.message.elements

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.breaktime.classmates.R
import com.breaktime.classmates.presentation.components.WebImage
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING
import com.breaktime.classmates.ui.theme.MEDIUM_PADDING
import com.breaktime.classmates.ui.theme.ProfileOutline
import com.breaktime.classmates.ui.theme.THE_SMALLEST_PADDING

@Composable
fun DialogsMessageItem(
    message: String,
    messageTime: String,
    isMyMessage: Boolean,
    isLastUserMessage: Boolean,
    isTimeNeeded: Boolean,
    isFoundMessage: Boolean = false,
    imageUrl: String? = null,
) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isFoundMessage) Color(0x556778E5)
                else Color.Transparent
            ),
        horizontalAlignment = if (isMyMessage) Alignment.End else Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            if (isLastUserMessage) imageUrl?.let {
                WebImage(
                    url = imageUrl,
                    modifier = Modifier
                        .padding(bottom = EXTRA_SMALL_PADDING)
                        .size(30.dp)
                        .clip(MaterialTheme.shapes.large)
                        .background(MaterialTheme.colors.ProfileOutline.copy(alpha = 0.5f)),
                    placeHolder = painterResource(R.drawable.no_user_photo),
                )
            }

            Box(modifier = Modifier
                .padding(THE_SMALLEST_PADDING)
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                        bottomStart = if (!isMyMessage && isLastUserMessage) 5.dp else 12.dp,
                        bottomEnd = if (isMyMessage && isLastUserMessage) 5.dp else 12.dp
                    )
                )
                .background(if (isMyMessage) Color(0xFF6C84F6) else Color(0xFFFFFFFD))
                .clickable {
                    isExpanded = !isExpanded
                }
                .padding(EXTRA_SMALL_PADDING)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = MEDIUM_PADDING),
                    text = message,
                    fontSize = 14.sp,
                    color = if (isMyMessage) Color(0xFFEEEEEE) else Color(0xFF888888)
                )
            }
        }
        val textHeightModifier = if (isTimeNeeded || isExpanded) Modifier else Modifier.height(0.dp)
        Text(
            modifier = textHeightModifier
                .padding(
                    vertical = THE_SMALLEST_PADDING,
                    horizontal = MEDIUM_PADDING
                )
                .animateContentSize(),
            text = messageTime,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}