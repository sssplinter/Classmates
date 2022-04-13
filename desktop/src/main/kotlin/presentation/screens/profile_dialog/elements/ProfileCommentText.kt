package presentation.screens.profile_dialog.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ui.theme.SMALL_PADDING
import ui.theme.THE_SMALLEST_PADDING
import util.dp

@Composable
fun ProfileCommentText(
    text: String,
    fontSize: TextUnit,
    imageVector: ImageVector? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SMALL_PADDING, vertical = THE_SMALLEST_PADDING),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            fontSize = fontSize,
            text = text
        )
        imageVector?.let {
            Icon(
                modifier = Modifier.size(14.sp.dp),
                imageVector = imageVector,
                contentDescription = "",
                tint = Color.Black
            )
        }
    }
}