package presentation.screens.chats_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import localization.Vocabulary
import navigation.component.NavHostController
import org.kodein.di.compose.rememberInstance
import presentation.components.SearchTextField
import presentation.components.dialogs.LoadingDialog
import presentation.screens.chats_screen.elements.DialogsChatItem
import presentation.screens.chats_screen.elements.DialogsMessageScreen
import presentation.screens.chats_screen.elements.MessageNoDialog
import ui.theme.EXTRA_SMALL_PADDING
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
            LazyColumn(
                modifier = Modifier
                    .width(250.dp)
                    .fillMaxHeight()
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
    if (showLoadingDialog.value) {
        LoadingDialog()
    }
}
