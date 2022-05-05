package com.breaktime.classmates.presentation.screens.message

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.breaktime.classmates.navigation.Screen
import com.breaktime.classmates.presentation.components.SearchTextField
import com.breaktime.classmates.presentation.components.dialogs.LoadingDialog
import com.breaktime.classmates.presentation.screens.message.elements.DialogsChatItem
import com.breaktime.classmates.ui.theme.MEDIUM_PADDING
import com.breaktime.classmates.ui.theme.SMALL_PADDING
import org.kodein.di.compose.rememberInstance

typealias FindMessageStatus = Triple<Int, Boolean, Boolean>

@Composable
fun MessagesScreen(globalNavController: NavHostController, navController: NavHostController) {
    val viewModel: MessagesScreenViewModel by rememberInstance()

    val messagesListState = rememberLazyListState()
    val searchText = remember { mutableStateOf("") }
    val findMessageStatus = remember { mutableStateOf(FindMessageStatus(-1, false, false)) }
    val chatsList = viewModel.chatsList
    val currentMessages = viewModel.currentChatData
    val showLoadingDialog = remember { mutableStateOf(false) }

    initChatsObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        messagesListState = messagesListState,
        showLoadingDialog = showLoadingDialog,
        findMessageStatus = findMessageStatus
    )

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTextField(
            modifier = Modifier
                .padding(MEDIUM_PADDING)
                .fillMaxWidth(),
            onValueChanged = {
                viewModel.setEvent(MessagesScreenContract.Event.OnSearchChatTextAppear(it))
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
                    DialogsChatItem(
                        isSelected = chatInfo.id == currentMessages.value?.first,
                        chatInfo = chatInfo,
                        onClick = {
                            globalNavController.currentBackStackEntry?.savedStateHandle?.set(
                                "chatInfo", chatInfo
                            )
                            globalNavController.navigate(Screen.Chat.route)
                                viewModel.setEvent(
                                    MessagesScreenContract.Event.OnSelectChat(
                                        chatInfo.id
                                    )
                                )
                        }
                    )
                }
            }
        }
    }
    if (showLoadingDialog.value) {
        LoadingDialog()
    }
}
