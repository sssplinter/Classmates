package com.breaktime.classmates.presentation.screens.friends

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.breaktime.classmates.R
import com.breaktime.classmates.domain.entities.data.UserInfo
import com.breaktime.classmates.localization.Vocabulary
import com.breaktime.classmates.presentation.components.SearchTextField
import com.breaktime.classmates.presentation.components.dialogs.SendMessageDialog
import com.breaktime.classmates.presentation.components.person_item.PersonItemOneButton
import com.breaktime.classmates.presentation.components.person_item.PersonItemTwoButton
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING
import com.breaktime.classmates.ui.theme.MEDIUM_PADDING
import com.breaktime.classmates.ui.theme.THE_SMALLEST_PADDING
import org.kodein.di.compose.rememberInstance

enum class ActiveTab { FRIENDS, SUBSCRIBERS, SUBSCRIPTIONS }

@Composable
fun FriendsScreen(navController: NavHostController) {
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
        SearchTextField(
            modifier = Modifier.padding(MEDIUM_PADDING).fillMaxWidth(),
            text = searchText,
            onValueChanged = {
                viewModel.setEvent(FriendsScreenContract.Event.OnSearchUserTextAppear(it))
            },
            hint = Vocabulary.localization.search
        )
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = MEDIUM_PADDING)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = EXTRA_SMALL_PADDING)
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
                        .padding(horizontal = EXTRA_SMALL_PADDING)
                        .animateContentSize(),
                    color = Color.Blue
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = EXTRA_SMALL_PADDING)
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
                        .padding(horizontal = EXTRA_SMALL_PADDING)
                        .animateContentSize(),
                    color = Color.Blue
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = EXTRA_SMALL_PADDING)
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
                        .padding(horizontal = EXTRA_SMALL_PADDING)
                        .animateContentSize(),
                    color = Color.Blue
                )
            }
        }
        LazyColumn(
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
                                actionImg1Src = R.drawable.send_message,
                                actionImg2Src = R.drawable.remove_friend,
                                onActionImg1Click = {
                                    viewModel.setEvent(
                                        FriendsScreenContract.Event.OnShowSendMessageClick(
                                            personInfo
                                        )
                                    )
                                },
                                onActionImg2Click = {
                                    viewModel.setEvent(
                                        FriendsScreenContract.Event.OnRemoveFriendClick(
                                            personInfo
                                        )
                                    )
                                }
                            )
                        }
                        UserInfo.UserRole.SUBSCRIBER -> {
                            PersonItemTwoButton(
                                userInfo = personInfo,
                                actionImg1Src = R.drawable.accept,
                                actionImg2Src = R.drawable.cancel,
                                onActionImg1Click = {
                                    viewModel.setEvent(
                                        FriendsScreenContract.Event.OnAcceptSubscriberClick(
                                            personInfo
                                        )
                                    )
                                },
                                onActionImg2Click = {
                                    viewModel.setEvent(
                                        FriendsScreenContract.Event.OnCancelSubscriberClick(
                                            personInfo
                                        )
                                    )
                                }
                            )
                        }
                        UserInfo.UserRole.SUBSCRIPTION -> {
                            PersonItemOneButton(
                                userInfo = personInfo,
                                actionImgSrc = R.drawable.cancel,
                                onActionImgClick = {
                                    viewModel.setEvent(
                                        FriendsScreenContract.Event.OnCancelSubscriptionClick(
                                            personInfo
                                        )
                                    )
                                },
                            )
                        }
                        UserInfo.UserRole.DEFAULT ->
                            PersonItemOneButton(
                                userInfo = personInfo,
                                actionImgSrc = R.drawable.add_friend,
                                onActionImgClick = {
                                    viewModel.setEvent(
                                        FriendsScreenContract.Event.OnAddFriendClick(
                                            personInfo
                                        )
                                    )
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
                viewModel.setEvent(
                    FriendsScreenContract.Event.OnSendMessageClick(
                        userToSendMessage.value!!,
                        it
                    )
                )
                userToSendMessage.value = null
            }
        )
    }
}