package presentation.screens.profile_dialog.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.theme.EXTRA_SMALL_PADDING

@Composable
fun ProfileDivider() {
    androidx.compose.material.Divider(
        modifier = Modifier.padding(vertical = EXTRA_SMALL_PADDING / 2),
        color = Color.Gray.copy(alpha = 0.5f)
    )
}