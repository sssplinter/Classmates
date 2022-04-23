package presentation.screens.people_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.entities.response.UsersInfoResponse
import navigation.component.NavHostController
import org.kodein.di.compose.rememberInstance
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.MEDIUM_PADDING

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PeopleScreen(navController: NavHostController) {
    val viewModel: PeopleScreenViewModel by rememberInstance()

    var searchText by remember { mutableStateOf("") }
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
                text = "Find your friends",
                textAlign = TextAlign.Center,
                fontSize = 28.sp
            )
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchText,
                onValueChange = { searchText = it },
                singleLine = true
            ) { innerTextField ->
                Row(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(EXTRA_SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(end = EXTRA_SMALL_PADDING),
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                    Box {
                        if (searchText.isEmpty()) {
                            Text(text = "Search")
                        }
                        innerTextField()
                    }
                }
            }
        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp)
        ) {
            peopleList.forEach { personInfo ->
                item {
                    PersonItem(personInfo)
                }
            }
        }
    }
}