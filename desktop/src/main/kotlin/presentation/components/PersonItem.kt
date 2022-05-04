package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
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
import domain.entities.data.UserInfo
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.MEDIUM_PADDING
import ui.theme.ProfileOutline

@Composable
fun PersonItem(userInfo: UserInfo, actionImgSrc: String, onActionImgClick: () -> Unit) {
    Box(modifier = Modifier
        .padding(EXTRA_SMALL_PADDING)
        .clip(MaterialTheme.shapes.medium)
        .background(Color(0xFFF5F5FC))
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(EXTRA_SMALL_PADDING),
            verticalAlignment = Alignment.CenterVertically) {
            WebImage(
                url = userInfo.profileImageUrl,
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colors.ProfileOutline.copy(alpha = 0.5f)),
                placeHolder = painterResource("no_user_photo.svg"),
            )

            Column(modifier = Modifier.fillMaxWidth(0.8f).padding(start = MEDIUM_PADDING)) {
                Text(
                    text = "${userInfo.name} ${userInfo.surname}",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = userInfo.bio,
                    fontSize = 10.sp,
                    maxLines = 2,
                    color = Color.Gray,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (actionImgSrc.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.CenterEnd)
                            .clip(RoundedCornerShape(15.dp))
                            .clickable {
                                onActionImgClick()
                            },
                        painter = painterResource(actionImgSrc),
                        contentDescription = "Action image",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}