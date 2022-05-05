package com.breaktime.classmates.presentation.screens.people

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.breaktime.classmates.R
import com.breaktime.classmates.domain.entities.data.UserInfo
import com.breaktime.classmates.localization.Vocabulary
import com.breaktime.classmates.presentation.components.SearchTextField
import com.breaktime.classmates.presentation.components.dialogs.SendMessageDialog
import com.breaktime.classmates.presentation.components.person_item.PersonItemOneButton
import com.breaktime.classmates.presentation.components.person_item.PersonItemTwoButton
import com.breaktime.classmates.ui.theme.MEDIUM_PADDING
import org.kodein.di.compose.rememberInstance

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
        SearchTextField(
            modifier = Modifier
                .padding(MEDIUM_PADDING)
                .fillMaxWidth(),
            text = searchText,
            onValueChanged = {
                viewModel.setEvent(PeopleScreenContract.Event.OnSearchUserTextAppear(it))
            },
            hint = Vocabulary.localization.search
        )

        LazyColumn(
            contentPadding = PaddingValues(8.dp)
        ) {
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
                                        PeopleScreenContract.Event.OnShowSendMessageClick(
                                            personInfo
                                        )
                                    )
                                },
                                onActionImg2Click = {
                                    viewModel.setEvent(
                                        PeopleScreenContract.Event.OnRemoveFriendClick(
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
                                        PeopleScreenContract.Event.OnAcceptSubscriberClick(
                                            personInfo
                                        )
                                    )
                                },
                                onActionImg2Click = {
                                    viewModel.setEvent(
                                        PeopleScreenContract.Event.OnCancelSubscriberClick(
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
                                        PeopleScreenContract.Event.OnCancelSubscriptionClick(
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
                                        PeopleScreenContract.Event.OnAddFriendClick(
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
                    PeopleScreenContract.Event.OnSendMessageClick(
                        userToSendMessage.value!!,
                        it
                    )
                )
                userToSendMessage.value = null
            }
        )
    }
}