package presentation.screens.create_group_dialog

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import localization.Vocabulary
import org.kodein.di.compose.rememberInstance
import presentation.components.RoundedTextField
import presentation.components.dialogs.LoadingDialog
import presentation.components.dialogs.Popup
import presentation.screens.create_group_dialog.elements.FriendItem
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.MEDIUM_PADDING
import ui.theme.SMALL_PADDING
import util.dp

@Composable
fun CreateGroupDialog(closeDialog: () -> Unit) {
    val viewModel: CreateGroupViewModel by rememberInstance()
    viewModel.loadFriends()

    val groupName = remember { mutableStateOf("") }
    val messageText = remember { mutableStateOf("") }
    val showLoadingDialog = remember { mutableStateOf(false) }
    val friendsList = mutableStateListOf<CreateGroupViewModel.PersonItem>()
    initGroupCreationObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        friendsList = friendsList,
        showLoadingDialog = showLoadingDialog,
        closeDialog = closeDialog
    )

    Popup {
        Column(
            modifier = Modifier
                .width(300.dp)
                .clickable(enabled = false, onClick = {})
                .animateContentSize()
                .clip(MaterialTheme.shapes.medium)
                .background(Color.White)
                .padding(MEDIUM_PADDING)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = EXTRA_SMALL_PADDING),
                text = Vocabulary.localization.createGroup,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SMALL_PADDING),
                text = Vocabulary.localization.createGroupName,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
            )
            RoundedTextField(
                modifier = Modifier
                    .padding(bottom = SMALL_PADDING)
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, RoundedCornerShape(5.dp)),
                text = groupName,
                hint = Vocabulary.localization.createGroupNameHint,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SMALL_PADDING),
                text = Vocabulary.localization.createGroupFirstMessage,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
            )
            RoundedTextField(
                modifier = Modifier
                    .padding(bottom = SMALL_PADDING)
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, RoundedCornerShape(5.dp)),
                text = messageText,
                hint = Vocabulary.localization.message,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SMALL_PADDING),
                text = Vocabulary.localization.createGroupChoosePeople,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
            )

            LazyColumn(modifier = Modifier.heightIn(60.dp, 200.dp),
                verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)) {
                friendsList.forEach {
                    item {
                        FriendItem(it)
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = MEDIUM_PADDING)) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.sp.dp)
                        .clickable(onClick = closeDialog),
                    text = Vocabulary.localization.cancel,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = Color(0xFF0C4CDD)
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.sp.dp)
                        .clickable {
                            if (groupName.value.isNotEmpty() && messageText.value.isNotEmpty() && friendsList.filter { it.isChecked }.size >= 2) {
                                viewModel.setEvent(
                                    CreateGroupContract.Event.OnCreateClick(
                                        groupName.value,
                                        messageText.value,
                                        friendsList.map { it.id }
                                    )
                                )
                            }
                        },
                    text = Vocabulary.localization.send,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = Color(0xFF0C4CDD)
                )
            }
        }
        if (showLoadingDialog.value) {
            LoadingDialog()
        }
    }
}
