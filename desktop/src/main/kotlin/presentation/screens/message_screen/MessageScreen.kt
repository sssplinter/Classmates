package presentation.screens.message_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.SMALL_PADDING

@Composable
fun MessageScreen() {
    var searchText by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(vertical = EXTRA_SMALL_PADDING)) {
            BasicTextField(
                modifier = Modifier.width(250.dp),
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
            LazyColumn(modifier = Modifier
                .width(250.dp)
                .fillMaxHeight()
                .padding(top = SMALL_PADDING)
            ) {
                item { MessageItem() }
                item { MessageItemActive() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
                item { MessageItem() }
            }
        }
    }
}