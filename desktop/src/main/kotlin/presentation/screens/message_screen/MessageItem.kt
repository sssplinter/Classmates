package presentation.screens.message_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.MEDIUM_PADDING
import ui.theme.ProfileOutline

@Composable
fun MessageItem() {
    Box(modifier = Modifier
        .padding(vertical = EXTRA_SMALL_PADDING)
        .clip(MaterialTheme.shapes.medium)
        .background(Color(0xFFF5F5FC))
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(MEDIUM_PADDING)) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colors.ProfileOutline.copy(alpha = 0.5f)),
                painter = painterResource("no_user_photo.svg"),
                contentDescription = ""
            )
            Column(modifier = Modifier.fillMaxWidth().padding(start = MEDIUM_PADDING)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "User name",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "05.06.2022",
                        fontSize = 8.sp,
                        color = Color.Gray
                    )
                }
                Text(
                    text = "messagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessage",
                    fontSize = 8.sp,
                    maxLines = 2,
                    color = Color.Gray,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Box(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(EXTRA_SMALL_PADDING / 2)
            .size(14.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color(0xFF65C876))
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "5",
                fontSize = 8.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun MessageItemActive() {
    Box(modifier = Modifier
        .padding(vertical = EXTRA_SMALL_PADDING)
        .clip(MaterialTheme.shapes.medium)
        .background(Color(0xFF6B86F8))
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(MEDIUM_PADDING)) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colors.ProfileOutline.copy(alpha = 0.5f)),
                painter = painterResource("no_user_photo.svg"),
                contentDescription = ""
            )
            Column(modifier = Modifier.fillMaxWidth().padding(start = MEDIUM_PADDING)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "User name",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "05.06.2022",
                        fontSize = 8.sp,
                        color = Color.White,
                    )
                }
                Text(
                    text = "messagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessage",
                    fontSize = 8.sp,
                    maxLines = 2,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}