package com.breaktime.classmates.presentation.components.person_item

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
import com.breaktime.classmates.domain.entities.data.UserInfo
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING

@Composable
fun PersonItemTwoButton(
    userInfo: UserInfo,
    actionImg1Src: Int,
    actionImg2Src: Int,
    onActionImg1Click: () -> Unit,
    onActionImg2Click: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(EXTRA_SMALL_PADDING)
            .clip(MaterialTheme.shapes.medium)
            .background(Color(0xFFF5F5FC))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(EXTRA_SMALL_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PersonItemInfo(userInfo)
            PersonItemImage(
                imgSrc = actionImg1Src,
                onClick = onActionImg1Click
            )
            PersonItemImage(
                imgSrc = actionImg2Src,
                onClick = onActionImg2Click
            )
        }
    }
}