package presentation.screens.profile_dialog.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import localization.Vocabulary
import presentation.screens.profile_dialog.ProfileScreenContract
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.THE_LARGEST_PADDING

@Composable
fun ProfileLogoutButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(top = THE_LARGEST_PADDING)
            .fillMaxWidth()
            .height(40.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color(0xFFDDDDDD))
            .clickable(onClick = onClick)
            .padding(EXTRA_SMALL_PADDING),
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            text = Vocabulary.localization.logOut,
            color = Color.Blue,
        )
    }
}