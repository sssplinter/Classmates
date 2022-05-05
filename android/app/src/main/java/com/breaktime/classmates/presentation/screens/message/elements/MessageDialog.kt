package com.breaktime.classmates.presentation.screens.message.elements

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.breaktime.classmates.R
import com.breaktime.classmates.domain.entities.data.ChatInfo
import com.breaktime.classmates.domain.entities.data.MessageInfo
import com.breaktime.classmates.presentation.components.RoundedTextField
import com.breaktime.classmates.presentation.components.SearchTextField
import com.breaktime.classmates.presentation.components.WebImage
//import presentation.screens.message_screen.FindMessageStatus
import com.breaktime.classmates.presentation.screens.message.FindMessageStatus
import com.breaktime.classmates.presentation.screens.message.MessageScreenContract
import com.breaktime.classmates.presentation.screens.message.chat.ChatViewModel
import com.breaktime.classmatei.theme.*
import com.breaktime.classmates.ui.theme.*

@Composable
fun MessageDialog(
    chatInfo: ChatInfo,
    messages: List<MessageInfo>,
    findMessageStatus: MutableState<FindMessageStatus>,
    messagesListState: LazyListState,
    viewModel: ChatViewModel,
) {
    val isSearchPanelVisible = viewModel.isSearchPanelVisible
    val searchMessageText = remember { mutableStateOf("") }
    val sendMessageText = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = THE_SMALLEST_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            WebImage(
                url = chatInfo.photoUrl,
                modifier = Modifier
                    .padding(start = EXTRA_LARGE_PADDING)
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colors.ProfileOutline.copy(alpha = 0.5f)),
                placeHolder = painterResource(R.drawable.no_user_photo),
            )
            Text(
                modifier = Modifier.padding(EXTRA_SMALL_PADDING),
                text = chatInfo.name,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        viewModel.setEvent(MessageScreenContract.Event.OnOpenCloseSearchClick)
                        searchMessageText.value = ""
                    }
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = Icons.Outlined.Search,
                    tint = Color.Gray,
                    contentDescription = ""
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = SMALL_PADDING, start = MEDIUM_PADDING)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFFF3F4FA))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MEDIUM_PADDING, vertical = SMALL_PADDING)
                    .height(if (isSearchPanelVisible.value) 25.dp else 0.dp)
                    .animateContentSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(45.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(if (findMessageStatus.value.second) Color(0xFF6C85F5) else Color(0xFFD7D7D7))
                        .clickable(enabled = findMessageStatus.value.second) {
                            viewModel.setEvent(MessageScreenContract.Event.OnPrevFoundMessageBtnClick)
                        }
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.Center),
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        tint = Color.White,
                        contentDescription = ""
                    )
                }
                Spacer(modifier = Modifier.width(5.dp).fillMaxHeight())
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(45.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(if (findMessageStatus.value.third) Color(0xFF6C85F5) else Color(0xFFD7D7D7))
                        .clickable(enabled = findMessageStatus.value.third) {
                            viewModel.setEvent(MessageScreenContract.Event.OnNextFoundMessageBtnClick)
                        }
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.Center),
                        imageVector = Icons.Outlined.KeyboardArrowUp,
                        tint = Color.White,
                        contentDescription = ""
                    )
                }
                Spacer(modifier = Modifier.width(15.dp).fillMaxHeight())
                SearchTextField(
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    text = searchMessageText,
                    onValueChanged = {
                        viewModel.setEvent(MessageScreenContract.Event.OnSearchMessageTextAppear(it))
                    },
                    hint = "Search message...",
                    fontSize = 10.sp,
                    hintFontSize = 10.sp,
                    borderColor = Color.Transparent,
                    backgroundColor = Color.White
                )
                Spacer(modifier = Modifier.width(5.dp).fillMaxHeight())
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color(0xFF6C85F5))
                        .clickable {
                            viewModel.setEvent(MessageScreenContract.Event.OnOpenCloseSearchClick)
                            searchMessageText.value = ""
                        }
                ) {
                    Icon(
                        modifier = Modifier.size(15.dp).align(Alignment.Center),
                        imageVector = Icons.Default.Close,
                        tint = Color.White,
                        contentDescription = ""
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize().weight(1f).animateContentSize(),
                state = messagesListState
            ) {
                messages.withIndex().forEach { (index, message) ->
                    item {
                        val backgroundColor =
                            if (findMessageStatus.value.first == index) Color.Blue else Color.Transparent
                        Box(modifier = Modifier.fillMaxWidth().background(backgroundColor)) {

                        }
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(MEDIUM_PADDING)) {
                RoundedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = SMALL_PADDING),
                    text = sendMessageText,
                    hint = "Message..."
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(if (sendMessageText.value.isNotEmpty()) Color(0xFF6C85F5) else Color(0xFFD7D7D7))
                        .clickable(enabled = sendMessageText.value.isNotEmpty()) {
                            viewModel.setEvent(MessageScreenContract.Event.OnSendMessageBtnClick(sendMessageText.value))
                            sendMessageText.value = ""
                        }
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.Center),
                        imageVector = Icons.Default.KeyboardArrowUp,
                        tint = Color.White,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}