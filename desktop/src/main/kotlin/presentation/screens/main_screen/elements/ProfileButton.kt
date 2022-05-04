package presentation.screens.main_screen.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import presentation.components.WebImage
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.ProfileOutline

@Composable
fun ProfileButton(
    imageUrl: String,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = Modifier
            .padding(EXTRA_SMALL_PADDING)
            .size(40.dp),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = MaterialTheme.colors.ProfileOutline
        ),
        onClick = onClick
    ) {
        WebImage(
            url = imageUrl,
            modifier = Modifier
                .padding(bottom = EXTRA_SMALL_PADDING)
                .size(30.dp)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colors.ProfileOutline.copy(alpha = 0.5f)),
            placeHolder = painterResource("no_user_photo.svg"),
        )
    }
}