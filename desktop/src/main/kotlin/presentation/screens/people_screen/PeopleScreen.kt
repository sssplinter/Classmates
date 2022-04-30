package presentation.screens.people_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.entities.data.UserInfo
import navigation.component.NavHostController
import org.kodein.di.compose.rememberInstance
import presentation.components.PersonItem
import presentation.components.SearchTextField
import ui.theme.MEDIUM_PADDING

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PeopleScreen(navController: NavHostController) {
    val viewModel: PeopleScreenViewModel by rememberInstance()

    val searchText = remember { mutableStateOf("") }
    val peopleList = viewModel.peopleList

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
                text = "Find all people",
                textAlign = TextAlign.Center,
                fontSize = 28.sp
            )
            SearchTextField(
                modifier = Modifier.fillMaxWidth(),
                text = searchText,
                hint = "Search"
            )
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
                    UserInfo.UserRole.DEFAULT -> "add_friend.svg" to {}
                    UserInfo.UserRole.ME -> return@forEach
                }
                item {
                    PersonItem(personInfo, actionImg, action)
                }
            }
        }
    }
}