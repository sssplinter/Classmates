package com.breaktime.classmates.presentation.screens.profile.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.breaktime.classmates.R
import com.breaktime.classmates.presentation.components.WebImage
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING
import com.breaktime.classmates.ui.theme.ProfileOutline

@Composable
fun ProfileImageButton(imageUrl: String, onClick: () -> Unit) {
    WebImage(
        url = imageUrl,
        modifier = Modifier
            .padding(end = EXTRA_SMALL_PADDING)
            .size(55.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(MaterialTheme.colors.ProfileOutline)
            .border(2.dp, Color.Black, RoundedCornerShape(40.dp))
            .clickable(onClick = onClick),
        placeHolder = painterResource(R.drawable.no_user_photo),
    )
}