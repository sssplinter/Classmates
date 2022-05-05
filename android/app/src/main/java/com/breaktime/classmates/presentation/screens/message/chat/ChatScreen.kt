package com.breaktime.classmates.presentation.screens.message

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.breaktime.classmates.domain.entities.data.ChatInfo
import com.breaktime.classmates.presentation.screens.message.chat.ChatViewModel
import com.breaktime.classmates.presentation.screens.message.elements.MessageDialog
import com.breaktime.classmates.presentation.screens.message.elements.MessageNoDialog
import com.breaktime.classmatei.theme.*
import org.kodein.di.compose.rememberInstance

@Composable
fun ChatScreen(navController: NavController, chatInfo: ChatInfo) {
    val viewModel: ChatViewModel by rememberInstance()
    val findMessageStatus = remember { mutableStateOf(FindMessageStatus(-1, false, false)) }
    val messagesListState = rememberLazyListState()

    val currentMessages = viewModel.currentMessages
    currentMessages.value?.let { (chatInfo, messages) ->
        MessageDialog(
            chatInfo,
            messages,
           findMessageStatus,
            messagesListState,
            viewModel
        )
    } ?: MessageNoDialog()
}