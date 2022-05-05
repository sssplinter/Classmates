package com.breaktime.classmates.presentation.components.person_item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.breaktime.classmates.R
import com.breaktime.classmates.domain.entities.data.UserInfo
import com.breaktime.classmates.localization.Vocabulary
import com.breaktime.classmates.presentation.components.WebImage
import com.breaktime.classmates.ui.theme.MEDIUM_PADDING
import com.breaktime.classmates.ui.theme.ProfileOutline

@Composable
fun RowScope.PersonItemInfo(userInfo: UserInfo) {
    WebImage(
        url = userInfo.profileImageUrl,
        modifier = Modifier
            .size(40.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colors.ProfileOutline.copy(alpha = 0.5f)),
        placeHolder = painterResource(R.drawable.no_user_photo),
    )

    Column(modifier = Modifier.weight(1f).padding(start = MEDIUM_PADDING)) {
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
}

@Composable
fun PersonItemImage(imgSrc: Int, onClick: () -> Unit) {
    Icon(
        modifier = Modifier
            .padding(3.dp)
            .size(30.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                onClick()
            },
        painter = painterResource(imgSrc),
        contentDescription = Vocabulary.localization.actionImgContent,
        tint = Color.Black
    )
}
