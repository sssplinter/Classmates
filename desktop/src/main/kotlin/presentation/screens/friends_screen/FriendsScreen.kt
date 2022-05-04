package presentation.screens.friends_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.entities.data.UserInfo
import presentation.components.SearchTextField
import presentation.components.PersonItem
import ui.theme.EXTRA_LARGE_PADDING
import ui.theme.MEDIUM_PADDING
import ui.theme.THE_SMALLEST_PADDING

enum class ActiveTab { FRIENDS, SUBSCRIBERS, SUBSCRIPTIONS }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendsScreen() {
    val activeTabState = remember { mutableStateOf(ActiveTab.FRIENDS) }
    val searchText = remember { mutableStateOf("") }
    var peopleList = mutableStateListOf<UserInfo>().apply {
        add(UserInfo("", userRole = UserInfo.UserRole.FRIEND))
        add(UserInfo("", userRole = UserInfo.UserRole.FRIEND))
        add(UserInfo("", userRole = UserInfo.UserRole.FRIEND))
        add(UserInfo("", userRole = UserInfo.UserRole.FRIEND))
        add(UserInfo("", userRole = UserInfo.UserRole.FRIEND))
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .padding(MEDIUM_PADDING)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.7f),
                text = "Your connections",
                textAlign = TextAlign.Center,
                fontSize = 28.sp
            )
            SearchTextField(
                modifier = Modifier.fillMaxWidth(),
                text = searchText,
                hint = "Search"
            )
        }
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = MEDIUM_PADDING)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = EXTRA_LARGE_PADDING)
                    .clickable {
                        activeTabState.value = ActiveTab.FRIENDS
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(vertical = THE_SMALLEST_PADDING),
                    text = "Friends",
                    fontSize = 16.sp
                )
                Divider(
                    modifier = (if (activeTabState.value == ActiveTab.FRIENDS) Modifier.fillMaxWidth()
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
                        activeTabState.value = ActiveTab.SUBSCRIBERS
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(vertical = THE_SMALLEST_PADDING),
                    text = "Subscribers",
                    fontSize = 16.sp
                )
                Divider(
                    modifier = (if (activeTabState.value == ActiveTab.SUBSCRIBERS) Modifier.fillMaxWidth()
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
                        activeTabState.value = ActiveTab.SUBSCRIPTIONS
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(vertical = THE_SMALLEST_PADDING),
                    text = "Subscriptions",
                    fontSize = 16.sp
                )
                Divider(
                    modifier = (if (activeTabState.value == ActiveTab.SUBSCRIPTIONS) Modifier.fillMaxWidth()
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
            peopleList.forEach { personInfo ->
                val (actionImg, action) = when (personInfo.userRole) {
                    UserInfo.UserRole.FRIEND -> "remove_friend.svg" to {}
                    UserInfo.UserRole.SUBSCRIBER -> "accept.svg" to {}
                    UserInfo.UserRole.SUBSCRIPTION -> "cancel.svg" to {}
                    UserInfo.UserRole.DEFAULT -> "add_friend" to {}
                    UserInfo.UserRole.ME -> return@forEach
                }
                item {
                    PersonItem(personInfo, actionImg, action)
                }
            }
        }
    }
}

@Preview
@Composable
fun FriendsScreenPreview() {
    FriendsScreen()
}