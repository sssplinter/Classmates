package presentation.screens.people_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.entities.data.UserInfo
import localization.Vocabulary
import navigation.component.NavHostController
import org.kodein.di.compose.rememberInstance
import presentation.components.SearchTextField
import presentation.components.dialogs.SendMessageDialog
import presentation.components.person_item.PersonItemOneButton
import presentation.components.person_item.PersonItemTwoButton
import ui.theme.MEDIUM_PADDING

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PeopleScreen(navController: NavHostController) {
    val viewModel: PeopleScreenViewModel by rememberInstance()

    val searchText = remember { mutableStateOf("") }
    val peopleList = viewModel.peopleList
    val userToSendMessage = remember { mutableStateOf<UserInfo?>(null) }

    initPeopleObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        userToSendMessage = userToSendMessage
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(MEDIUM_PADDING)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.7f),
                text = Vocabulary.localization.findAllPeople,
                textAlign = TextAlign.Center,
                fontSize = 28.sp
            )
            SearchTextField(
                modifier = Modifier.fillMaxWidth(),
                text = searchText,
                onValueChanged = {
                    viewModel.setEvent(PeopleScreenContract.Event.OnSearchUserTextAppear(it))
                },
                hint = Vocabulary.localization.search
            )
        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp)
        ) {
            peopleList.forEach { personInfo ->
                item {
                    when (personInfo.userRole) {
                        UserInfo.UserRole.FRIEND -> {
                            PersonItemTwoButton(
                                userInfo = personInfo,
                                actionImg1Src = "send_message.svg",
                                actionImg2Src = "remove_friend.svg",
                                onActionImg1Click = {
                                    viewModel.setEvent(PeopleScreenContract.Event.OnShowSendMessageClick(personInfo))
                                },
                                onActionImg2Click = {
                                    viewModel.setEvent(PeopleScreenContract.Event.OnRemoveFriendClick(personInfo))
                                }
                            )
                        }
                        UserInfo.UserRole.SUBSCRIBER -> {
                            PersonItemTwoButton(
                                userInfo = personInfo,
                                actionImg1Src = "accept.svg",
                                actionImg2Src = "cancel.svg",
                                onActionImg1Click = {
                                    viewModel.setEvent(PeopleScreenContract.Event.OnAcceptSubscriberClick(personInfo))
                                },
                                onActionImg2Click = {
                                    viewModel.setEvent(PeopleScreenContract.Event.OnCancelSubscriberClick(personInfo))
                                }
                            )
                        }
                        UserInfo.UserRole.SUBSCRIPTION -> {
                            PersonItemOneButton(
                                userInfo = personInfo,
                                actionImgSrc = "cancel.svg",
                                onActionImgClick = {
                                    viewModel.setEvent(PeopleScreenContract.Event.OnCancelSubscriptionClick(personInfo))
                                },
                            )
                        }
                        UserInfo.UserRole.DEFAULT ->
                            PersonItemOneButton(
                                userInfo = personInfo,
                                actionImgSrc = "add_friend.svg",
                                onActionImgClick = {
                                    viewModel.setEvent(PeopleScreenContract.Event.OnAddFriendClick(personInfo))
                                },
                            )
                        UserInfo.UserRole.ME -> return@item
                    }
                }
            }
        }
    }

    if (userToSendMessage.value != null) {
        SendMessageDialog(
            onClickCancel = {
                userToSendMessage.value = null
            },
            onClickSend = {
                viewModel.setEvent(PeopleScreenContract.Event.OnSendMessageClick(userToSendMessage.value!!, it))
                userToSendMessage.value = null
            }
        )
    }
}