package presentation.screens.chats_screen.elements

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import presentation.components.WebImage
import ui.theme.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DialogsMessageItem(
    message: String,
    messageTime: String,
    isMyMessage: Boolean,
    fromUserName: String,
    isLastUserMessage: Boolean,
    isTimeNeeded: Boolean,
    isFoundMessage: Boolean = false,
    imageUrl: String? = null,
) {
    var showFromUser by remember { mutableStateOf(false) }
    var showTime by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isFoundMessage) Color(0x556778E5)
                else Color.Transparent
            )
            .animateContentSize(),
        horizontalAlignment = if (isMyMessage) Alignment.End else Alignment.Start
    ) {
        val fromUserTextHeightModifier = if (!isMyMessage && showFromUser) Modifier else Modifier.height(0.dp)
        Text(
            modifier = fromUserTextHeightModifier
                .padding(
                    horizontal = MEDIUM_PADDING
                )
                .padding(top = SMALL_PADDING)
                .animateContentSize(),
            text = fromUserName,
            fontSize = 12.sp,
            color = Color.Blue
        )
        Row(verticalAlignment = Alignment.Bottom) {
            if (isLastUserMessage) imageUrl?.let {
                WebImage(
                    url = imageUrl,
                    modifier = Modifier
                        .padding(bottom = EXTRA_SMALL_PADDING)
                        .size(30.dp)
                        .clip(MaterialTheme.shapes.large)
                        .background(MaterialTheme.colors.ProfileOutline.copy(alpha = 0.5f)),
                    placeHolder = painterResource("no_user_photo.svg"),
                )
            }

            Box(modifier = Modifier
                .padding(THE_SMALLEST_PADDING)
                .clip(RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = if (!isMyMessage && isLastUserMessage) 5.dp else 12.dp,
                    bottomEnd = if (isMyMessage && isLastUserMessage) 5.dp else 12.dp
                ))
                .background(if (isMyMessage) Color(0xFF6C84F6) else Color(0xFFFFFFFD))
                .combinedClickable(
                    onClick = {
                        showFromUser = !showFromUser
                    },
                    onLongClick = {
                        showTime = !showTime
                    }
                )
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
        val textHeightModifier = if (isTimeNeeded || showTime) Modifier else Modifier.height(0.dp)
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