package com.breaktime.classmates.presentation.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.breaktime.classmates.R
import com.breaktime.classmates.ui.theme.MEDIUM_PADDING
import com.breaktime.classmates.ui.theme.SMALL_PADDING

@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(MEDIUM_PADDING)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Settings",
                textAlign = TextAlign.Center,
                fontSize = 28.sp
            )
        }
        Row(
            modifier = Modifier
                .padding(SMALL_PADDING)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Theme",
                textAlign = TextAlign.Center,
                fontSize = 22.sp
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            val themesList = mutableListOf(
                R.drawable.ic_uk_flag to false,
                R.drawable.ic_ru_flag to false,
                R.drawable.ic_fr_flag to true
            )
            themesList.forEach { (theme, isActive) ->
                Box(
                    modifier = Modifier
                        .padding(MEDIUM_PADDING)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Gray)
                        .border(
                            if (isActive) 4.dp else 1.dp,
                            if (isActive) Color(0xFF419EF6) else Color.Black,
                            RoundedCornerShape(5.dp)
                        )
                ) {
                    Image(
                        modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .align(Alignment.Center),
                        painter = painterResource(theme),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "",
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(SMALL_PADDING)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Language",
                textAlign = TextAlign.Center,
                fontSize = 22.sp
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            val languagesFlagsList = mapOf(
                R.drawable.ic_uk_flag to false,
                R.drawable.ic_ru_flag to false,
                R.drawable.ic_fr_flag to true
            )
            languagesFlagsList.forEach { (flag, isActive) ->
                Box(
                    modifier = Modifier
                        .padding(MEDIUM_PADDING)
                        .border(
                            if (isActive) 4.dp else 1.dp,
                            if (isActive) Color(0xFF419EF6) else Color.Black,
                            RoundedCornerShape(5.dp)
                        )
                ) {
                    Image(
                        modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(5.dp)),
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(flag),
                        contentDescription = "",
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}