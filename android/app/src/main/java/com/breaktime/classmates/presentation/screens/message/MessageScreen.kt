package com.breaktime.classmates.presentation.screens.message

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.breaktime.classmates.R
import com.breaktime.classmates.navigation.Screen
import org.kodein.di.compose.rememberInstance
import com.breaktime.classmates.presentation.components.SearchTextField
import com.breaktime.classmates.presentation.screens.message.elements.MessageItem
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING
import com.breaktime.classmates.ui.theme.SMALL_PADDING

typealias FindMessageStatus = Triple<Int, Boolean, Boolean>

@Composable
fun MessageScreen(navController: NavHostController) {
    val viewModel: MessageScreenViewModel by rememberInstance()

    val messagesListState = rememberLazyListState()
    val searchText = remember { mutableStateOf("") }
    val findMessageStatus = remember { mutableStateOf(FindMessageStatus(-1, false, false)) }
    val chatsList = viewModel.chatsList
    val currentMessages = viewModel.currentMessages
    val showNoInternetConnectionDialog = remember { mutableStateOf(false) }
    val showLoadingDialog = remember { mutableStateOf(false) }

    initMessageObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        messagesListState = messagesListState,
        showNoInternetConnectionDialog = showNoInternetConnectionDialog,
        showLoadingDialog = showLoadingDialog,
        findMessageStatus = findMessageStatus
    )

    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(top = EXTRA_SMALL_PADDING)) {
            SearchTextField(
                modifier = Modifier.width(250.dp),
                onValueChanged = {
                    viewModel.setEvent(MessageScreenContract.Event.OnSearchChatTextAppear(it))
                },
                text = searchText,
                hint = "Search"
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = SMALL_PADDING)
            ) {
                chatsList.forEach { chatInfo ->
                    item {
                        MessageItem(
                            chatInfo = chatInfo,
                            onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "chatInfo", chatInfo
                                )
                                navController.navigate(Screen.Chat.route)
//                               viewModel.setEvent(MessageScreenContract.Event.OnSelectChat(chatInfo.id))
                            }
                        )
                    }
                }
            }
        }

//        currentMessages.value?.let { (chatInfo, messages) ->
//            MessageDialog(
//                chatInfo,
//                messages,
//                findMessageStatus,
//                messagesListState,
//                viewModel
//            )
//        } ?: MessageNoDialog()
    }
}
