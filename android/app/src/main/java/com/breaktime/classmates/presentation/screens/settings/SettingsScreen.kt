package com.breaktime.classmates.presentation.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.breaktime.classmates.domain.entities.data.Languages
import com.breaktime.classmates.domain.entities.data.Themes
import com.breaktime.classmates.localization.Vocabulary
import org.kodein.di.compose.rememberInstance
import com.breaktime.classmates.ui.theme.MEDIUM_PADDING
import com.breaktime.classmates.ui.theme.SMALL_PADDING
import com.breaktime.classmates.ui.theme.loginActiveButton

@Composable
fun SettingsScreen(navController: NavHostController) {
    val viewModel: SettingsScreenViewModel by rememberInstance()

    val currentTheme = remember { mutableStateOf(Themes.DAY) }
    val currentLanguage = remember { mutableStateOf(Languages.EN) }
    initSettingsObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        currentTheme = currentTheme,
        currentLanguage = currentLanguage
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(MEDIUM_PADDING)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = Vocabulary.localization.settings,
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
                text = Vocabulary.localization.theme,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.loginActiveButton,
                fontSize = 22.sp
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Themes.values().forEach { theme ->
                val isActive = theme == currentTheme.value
                Box(
                    modifier = Modifier
                        .padding(MEDIUM_PADDING)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Gray)
                        .border(if (isActive) 3.dp else 1.dp,
                            if (isActive) Color(0xFF419EF6) else Color.Black,
                            RoundedCornerShape(5.dp))
                        .clickable {
                            viewModel.setEvent(SettingsScreenContract.Event.OnThemeClick(theme))
                        }
                ) {
                    Image(
                        modifier = Modifier.height(50.dp).width(80.dp).align(Alignment.Center),
                        painter = painterResource(theme.imgSrc),
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
                text = Vocabulary.localization.language,
                textAlign = TextAlign.Center,
                fontSize = 22.sp
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Languages.values().forEach { language ->
                val isActive = language == currentLanguage.value
                Box(
                    modifier = Modifier
                        .padding(MEDIUM_PADDING)
                        .border(if (isActive) 3.dp else 1.dp,
                            if (isActive) Color(0xFF419EF6) else Color.Black,
                            RoundedCornerShape(5.dp))
                        .clickable {
                            viewModel.setEvent(SettingsScreenContract.Event.OnLanguageClick(language))
                        }
                ) {
                    Image(
                        modifier = Modifier
                            .height(50.dp)
                            .width(80.dp)
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(5.dp)),
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(language.imgSrc),
                        contentDescription = "",
                    )
                }
            }
        }
    }
}