package presentation.components.person_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import domain.entities.data.UserInfo
import ui.theme.EXTRA_SMALL_PADDING

@Composable
fun PersonItemTwoButton(
    userInfo: UserInfo,
    actionImg1Src: String,
    actionImg2Src: String,
    onActionImg1Click: () -> Unit,
    onActionImg2Click: () -> Unit,
) {
    Box(modifier = Modifier
        .padding(EXTRA_SMALL_PADDING)
        .clip(MaterialTheme.shapes.medium)
        .background(Color(0xFFF5F5FC))
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(EXTRA_SMALL_PADDING),
            verticalAlignment = Alignment.CenterVertically) {
            PersonItemInfo(userInfo)
            if (actionImg1Src.isNotEmpty()) {
                PersonItemImage(
                    imgSrc = actionImg1Src,
                    onClick = onActionImg1Click
                )
            }
            if (actionImg2Src.isNotEmpty()) {
                PersonItemImage(
                    imgSrc = actionImg2Src,
                    onClick = onActionImg2Click
                )
            }
        }
    }
}