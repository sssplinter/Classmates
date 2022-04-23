package presentation.screens.message_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import navigation.component.NavHostController
import org.kodein.di.compose.rememberInstance
import presentation.components.LoadingDialog
import presentation.components.NoInternetDialog
import presentation.components.SearchTextField
import presentation.screens.message_screen.elements.MessageDialog
import presentation.screens.message_screen.elements.MessageItem
import presentation.screens.message_screen.elements.MessageNoDialog
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.SMALL_PADDING

typealias FindMessageStatus = Triple<Int, Boolean, Boolean>

@Composable
fun MessageScreen(navController: NavHostController) {
    val viewModel: MessageScreenViewModel by rememberInstance()

    val searchText = remember { mutableStateOf("") }
    val findMessageStatus = remember { mutableStateOf(FindMessageStatus(-1, false, false)) }
    val chatsList = viewModel.chatsList
    val currentMessages = viewModel.currentMessages
    val showNoInternetConnectionDialog = remember { mutableStateOf(false) }
    val showLoadingDialog = remember { mutableStateOf(false) }

    initMessageObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        showNoInternetConnectionDialog = showNoInternetConnectionDialog,
        showLoadingDialog = showLoadingDialog,
        findMessageStatus = findMessageStatus
    )

    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(top = EXTRA_SMALL_PADDING)) {
            SearchTextField(
                modifier = Modifier.width(250.dp),
                text = searchText,
                hint = "Search"
            )
            LazyColumn(
                modifier = Modifier
                    .width(250.dp)
                    .fillMaxHeight()
                    .padding(top = SMALL_PADDING)
            ) {
                chatsList.forEach { chatInfo ->
                    item {
                        MessageItem(
                            chatInfo = chatInfo,
                            onClick = {
                                viewModel.setEvent(MessageScreenContract.Event.OnSelectChat(chatInfo.id))
                            }
                        )
                    }
                }
            }
        }
        currentMessages.value?.let { (chatInfo, messages) ->
            MessageDialog(
                chatInfo,
                messages,
                findMessageStatus,
                viewModel
            )
        } ?: MessageNoDialog()
    }

    if (showNoInternetConnectionDialog.value) {
        NoInternetDialog(
            iconSrc = "no_wifi.png",
            message = "No internet connection",
            actionMessage = "Press to repeat",
            backgroundColor = Color.White,
            shape = MaterialTheme.shapes.medium,
            onClick = {
//                viewModel.setEvent()
            }
        )
    }

    if (showLoadingDialog.value) {
        LoadingDialog(
            backgroundColor = Color.White,
            shape = MaterialTheme.shapes.medium
        )
    }
}
