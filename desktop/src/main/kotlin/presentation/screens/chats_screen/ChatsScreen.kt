package presentation.screens.chats_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import localization.Vocabulary
import navigation.component.NavHostController
import org.kodein.di.compose.rememberInstance
import presentation.components.SearchTextField
import presentation.components.dialogs.LoadingDialog
import presentation.screens.chats_screen.elements.DialogsChatItem
import presentation.screens.chats_screen.elements.DialogsMessageScreen
import presentation.screens.chats_screen.elements.MessageNoDialog
import presentation.screens.create_group_dialog.CreateGroupDialog
import ui.theme.EXTRA_LARGE_PADDING
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.MEDIUM_PADDING
import ui.theme.SMALL_PADDING

typealias FindMessageStatus = Triple<Int, Boolean, Boolean>

@Composable
fun ChatsScreen(navController: NavHostController) {
    val viewModel: ChatsScreenViewModel by rememberInstance()

    val messagesListState = rememberLazyListState()
    val searchText = remember { mutableStateOf("") }
    val findMessageStatus = remember { mutableStateOf(FindMessageStatus(-1, false, false)) }
    val chatsList = viewModel.chatsList
    val currentMessages = viewModel.currentChatData
    val showLoadingDialog = remember { mutableStateOf(false) }
    val showCreateGroupDialog = remember { mutableStateOf(false) }

    initChatsObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        messagesListState = messagesListState,
        showLoadingDialog = showLoadingDialog,
        findMessageStatus = findMessageStatus
    )

    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(top = EXTRA_SMALL_PADDING)) {
            SearchTextField(
                modifier = Modifier.width(250.dp),
                onValueChanged = {
                    viewModel.setEvent(ChatsScreenContract.Event.OnSearchChatTextAppear(it))
                },
                text = searchText,
                hint = Vocabulary.localization.search
            )
            Box(
                modifier = Modifier
                    .width(250.dp)
                    .fillMaxHeight()
            ) {
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
                                    viewModel.setEvent(ChatsScreenContract.Event.OnSelectChat(chatInfo.id))
                                }
                            )
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(horizontal = MEDIUM_PADDING, vertical = EXTRA_LARGE_PADDING)
                        .size(40.dp)
                        .align(Alignment.BottomEnd),
                    shape = RoundedCornerShape(20.dp),
                    onClick = { showCreateGroupDialog.value = true }
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center,
                        text = "+",
                        fontSize = 16.sp
                    )
                }
            }
        }
        currentMessages.value?.let { (currentChatId, chatInfo, messages) ->
            DialogsMessageScreen(
                chatInfo,
                messages,
                findMessageStatus,
                messagesListState,
                viewModel
            )
        } ?: MessageNoDialog()
    }
    if (showCreateGroupDialog.value) {
        CreateGroupDialog(
            closeDialog = {
                showCreateGroupDialog.value = false
            }
        )
    }
    if (showLoadingDialog.value) {
        LoadingDialog()
    }
}
