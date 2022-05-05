package presentation.screens.friends_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.entities.data.UserInfo
import localization.Vocabulary
import org.kodein.di.compose.rememberInstance
import presentation.components.SearchTextField
import presentation.components.dialogs.SendMessageDialog
import presentation.components.person_item.PersonItemOneButton
import presentation.components.person_item.PersonItemTwoButton
import presentation.screens.people_screen.PeopleScreenContract
import ui.theme.EXTRA_LARGE_PADDING
import ui.theme.MEDIUM_PADDING
import ui.theme.THE_SMALLEST_PADDING

enum class ActiveTab { FRIENDS, SUBSCRIBERS, SUBSCRIPTIONS }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendsScreen() {
    val viewModel: FriendsScreenViewModel by rememberInstance()

    var activeTabState by remember { mutableStateOf(ActiveTab.FRIENDS) }
    val searchText = remember { mutableStateOf("") }
    val friendsList = viewModel.friendsList
    val subscribersList = viewModel.subscribersList
    val subscriptionsList = viewModel.subscriptionsList
    val userToSendMessage = remember { mutableStateOf<UserInfo?>(null) }

    initFriendsObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        userToSendMessage = userToSendMessage
    )

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .padding(MEDIUM_PADDING)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.7f),
                text = Vocabulary.localization.yourConnections,
                textAlign = TextAlign.Center,
                fontSize = 28.sp
            )
            SearchTextField(
                modifier = Modifier.fillMaxWidth(),
                text = searchText,
                onValueChanged = {
                    viewModel.setEvent(FriendsScreenContract.Event.OnSearchUserTextAppear(it))
                },
                hint = Vocabulary.localization.search
            )
        }
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = MEDIUM_PADDING)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = EXTRA_LARGE_PADDING)
                    .clickable {
                        activeTabState = ActiveTab.FRIENDS
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(vertical = THE_SMALLEST_PADDING),
                    text = Vocabulary.localization.friends,
                    fontSize = 16.sp
                )
                Divider(
                    modifier = (if (activeTabState == ActiveTab.FRIENDS) Modifier.fillMaxWidth()
                    else Modifier.width(0.dp))
                        .height(2.dp)
                        .padding(horizontal = EXTRA_LARGE_PADDING)
                        .animateContentSize(),
                    color = Color.Blue
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = EXTRA_LARGE_PADDING)
                    .clickable {
                        activeTabState = ActiveTab.SUBSCRIBERS
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(vertical = THE_SMALLEST_PADDING),
                    text = Vocabulary.localization.subscribers,
                    fontSize = 16.sp
                )
                Divider(
                    modifier = (if (activeTabState == ActiveTab.SUBSCRIBERS) Modifier.fillMaxWidth()
                    else Modifier.width(0.dp))
                        .height(2.dp)
                        .padding(horizontal = EXTRA_LARGE_PADDING)
                        .animateContentSize(),
                    color = Color.Blue
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = EXTRA_LARGE_PADDING)
                    .clickable {
                        activeTabState = ActiveTab.SUBSCRIPTIONS
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(vertical = THE_SMALLEST_PADDING),
                    text = Vocabulary.localization.subscriptions,
                    fontSize = 16.sp
                )
                Divider(
                    modifier = (if (activeTabState == ActiveTab.SUBSCRIPTIONS) Modifier.fillMaxWidth()
                    else Modifier.width(0.dp))
                        .height(2.dp)
                        .padding(horizontal = EXTRA_LARGE_PADDING)
                        .animateContentSize(),
                    color = Color.Blue
                )
            }
        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp)
        ) {
            val peopleList = when (activeTabState) {
                ActiveTab.FRIENDS -> friendsList
                ActiveTab.SUBSCRIBERS -> subscribersList
                ActiveTab.SUBSCRIPTIONS -> subscriptionsList
            }

            peopleList.forEach { personInfo ->
                item {
                    when (personInfo.userRole) {
                        UserInfo.UserRole.FRIEND -> {
                            PersonItemTwoButton(
                                userInfo = personInfo,
                                actionImg1Src = "send_message.svg",
                                actionImg2Src = "remove_friend.svg",
                                onActionImg1Click = {
                                    viewModel.setEvent(FriendsScreenContract.Event.OnShowSendMessageClick(personInfo))
                                },
                                onActionImg2Click = {
                                    viewModel.setEvent(FriendsScreenContract.Event.OnRemoveFriendClick(personInfo))
                                }
                            )
                        }
                        UserInfo.UserRole.SUBSCRIBER -> {
                            PersonItemTwoButton(
                                userInfo = personInfo,
                                actionImg1Src = "accept.svg",
                                actionImg2Src = "cancel.svg",
                                onActionImg1Click = {
                                    viewModel.setEvent(FriendsScreenContract.Event.OnAcceptSubscriberClick(personInfo))
                                },
                                onActionImg2Click = {
                                    viewModel.setEvent(FriendsScreenContract.Event.OnCancelSubscriberClick(personInfo))
                                }
                            )
                        }
                        UserInfo.UserRole.SUBSCRIPTION -> {
                            PersonItemOneButton(
                                userInfo = personInfo,
                                actionImgSrc = "cancel.svg",
                                onActionImgClick = {
                                    viewModel.setEvent(FriendsScreenContract.Event.OnCancelSubscriptionClick(personInfo))
                                },
                            )
                        }
                        UserInfo.UserRole.DEFAULT ->
                            PersonItemOneButton(
                                userInfo = personInfo,
                                actionImgSrc = "add_friend.svg",
                                onActionImgClick = {
                                    viewModel.setEvent(FriendsScreenContract.Event.OnAddFriendClick(personInfo))
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
                viewModel.setEvent(FriendsScreenContract.Event.OnSendMessageClick(userToSendMessage.value!!, it))
                userToSendMessage.value = null
            }
        )
    }
}