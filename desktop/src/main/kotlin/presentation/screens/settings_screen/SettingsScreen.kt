package presentation.screens.settings_screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.MEDIUM_PADDING
import ui.theme.SMALL_PADDING

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
            val themesList = mutableStateListOf("day_mode1.png" to false, "night_mode1.png" to false, "day_night_mode1.png" to true)
            themesList.forEach { (theme, isActive) ->
                Box(
                    modifier = Modifier
                        .padding(MEDIUM_PADDING)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Gray)
                        .border(if (isActive) 4.dp else 1.dp, if (isActive) Color(0xFF419EF6) else Color.Black, RoundedCornerShape(5.dp))
                ) {
                    Image(
                        modifier = Modifier.height(50.dp).width(80.dp).align(Alignment.Center),
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
            val languagesFlagsList = mapOf("flags/uk_flag.svg" to false, "flags/french_flag.svg" to true, "flags/ru_flag.svg" to false)
            languagesFlagsList.forEach { (flag, isActive) ->
                Box(
                    modifier = Modifier
                        .padding(MEDIUM_PADDING)
                        .border(if (isActive) 4.dp else 1.dp, if (isActive) Color(0xFF419EF6) else Color.Black, RoundedCornerShape(5.dp))
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