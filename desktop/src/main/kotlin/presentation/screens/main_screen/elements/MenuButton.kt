package presentation.screens.main_screen.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ui.theme.EXTRA_SMALL_PADDING

@Composable
fun MenuButton(icon: ImageVector, isActive: Boolean, isNotified: Boolean = false, onClick: () -> Unit) {
    TextButton(
        modifier = Modifier.padding(EXTRA_SMALL_PADDING).size(40.dp),
        colors = ButtonDefaults.textButtonColors(backgroundColor = if (isActive) Color(0xFF6C85F7) else Color.Transparent),
        onClick = onClick
    ) {
        Box {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = if (isActive) Color.White else Color(0xFF656F7E)
            )
            if (isNotified && !isActive) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(5.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color(0xFF65C876))
                )
            }
        }
    }
}