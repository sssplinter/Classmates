package presentation.screens.create_group_dialog.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.screens.create_group_dialog.CreateGroupViewModel
import ui.theme.EXTRA_SMALL_PADDING

@Composable
fun FriendItem(personItem: CreateGroupViewModel.PersonItem) {
    val switchState = remember { mutableStateOf(personItem.isChecked) }
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(Color(0xFFF5F5FC))
            .padding(EXTRA_SMALL_PADDING)
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = personItem.name,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Black
        )
        Switch(
            checked = switchState.value,
            onCheckedChange = {
                switchState.value = it
                personItem.isChecked = it
            }
        )
    }
}