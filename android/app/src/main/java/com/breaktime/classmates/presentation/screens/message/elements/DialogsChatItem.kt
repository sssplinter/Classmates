package com.breaktime.classmates.presentation.screens.message.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.breaktime.classmates.domain.entities.data.ChatInfo
import com.breaktime.classmates.presentation.components.WebImage
import com.breaktime.classmates.ui.theme.MEDIUM_PADDING
import com.breaktime.classmates.ui.theme.ProfileOutline
import com.breaktime.classmates.ui.theme.SMALL_PADDING
import com.breaktime.classmates.ui.theme.THE_SMALLEST_PADDING
import com.breaktime.classmates.util.toTime
import com.breaktime.classmates.R

@Composable
fun DialogsChatItem(
    isSelected: Boolean,
    chatInfo: ChatInfo,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(bottom = SMALL_PADDING)
            .clip(MaterialTheme.shapes.medium)
            .background(if (isSelected) Color(0xFF6B86F8) else Color(0xFFF3F4FA))
            .clickable {
                onClick()
            }
    ) {

        Row(modifier = Modifier.fillMaxWidth().padding(MEDIUM_PADDING)) {
            WebImage(
                url = chatInfo.photoUrl,
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colors.ProfileOutline.copy(alpha = 0.5f)),
                placeHolder = painterResource(R.drawable.no_user_photo),
            )
            Column(modifier = Modifier.fillMaxWidth().padding(start = MEDIUM_PADDING)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chatInfo.name,
                        fontSize = 14.sp,
                        color = if (isSelected) Color.White else Color.Black,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = chatInfo.lastMessageDate.toTime(),
                        fontSize = 10.sp,
                        color = if (isSelected) Color.White else Color.Gray,
                    )
                }
                Text(
                    text = chatInfo.lastMessage,
                    fontSize = 10.sp,
                    maxLines = 2,
                    color = if (isSelected) Color.White else Color.Gray,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(THE_SMALLEST_PADDING)
                .size(14.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(Color(0xFF65C876))
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = chatInfo.unreadMessagesAmount.toString(),
                fontSize = 8.sp,
                color = Color.White
            )
        }
    }
}