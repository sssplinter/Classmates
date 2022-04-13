package presentation.screens.profile_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import navigation.component.NavHostController
import presentation.components.ShadowBox
import presentation.screens.profile_dialog.elements.*
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.MEDIUM_PADDING
import ui.theme.SMALL_PADDING

@Composable
fun ProfileScreen(navController: NavHostController, onOutBoxClick: () -> Unit) {
    val name = remember { mutableStateOf("name") }
    val surname = remember { mutableStateOf("surname") }
    val patronymic = remember { mutableStateOf("patronymic") }
    val bio = remember { mutableStateOf("bio") }

    ShadowBox(onOutBoxClick)

    Column(
        modifier = Modifier
            .width(350.dp)
            .clickable(enabled = false, onClick = {})
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
            .padding(SMALL_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ProfileRowContainer(backgroundColor = Color(0xFFEFEFEF)) {
            ProfileImageButton(
                image = "no_user_photo.svg",
                onClick = {}
            )
            Column(modifier = Modifier.padding(start = EXTRA_SMALL_PADDING)) {
                ProfileTextField(text = name)
                ProfileDivider()
                ProfileTextField(text = surname)
                ProfileDivider()
                ProfileTextField(text = patronymic)
            }
        }
        ProfileCommentText(
            text = "Enter your name and add a profile photo",
            fontSize = 12.sp
        )
        Column(modifier = Modifier.padding(vertical = SMALL_PADDING)) {
            ProfileCommentText(
                text = "BIO",
                fontSize = 14.sp
            )
            ProfileColumnContainer(backgroundColor = Color(0xFFEFEFEF)) {
                ProfileTextField(text = bio)
            }

            ProfileCommentText(
                text = "Any details such as age, occupation or city",
                fontSize = 12.sp
            )
        }

        val university = listOf(
            Triple("BSUIR", "Poit", "951006"),
            Triple("BSU", "Math", "103id4"),
            Triple("BSU", "Math", "103id4"),
            Triple("BSU", "Math", "103id4"),
            Triple("BSU", "Math", "103id4"),
        )

        ProfileCommentText(
            text = "Education",
            fontSize = 14.sp,
            imageVector = Icons.Default.Add
        )
        ProfileColumnContainer(
            modifier = Modifier
                .padding(bottom = SMALL_PADDING)
                .heightIn(max = 140.dp),
            backgroundColor = Color(0xFFEFEFEF)
        ) {
            LazyColumn {
                itemsIndexed(university) { index, (universityName, speciality, group) ->
                    RowUniversityItem(universityName, speciality, group)
                    if (index < university.lastIndex)
                        ProfileDivider()
                }
            }
        }
        ProfileColumnContainer(
            modifier = Modifier
                .padding(top = MEDIUM_PADDING),
            backgroundColor = Color(0xFFDDDDDD)
        ) {
            ProfileClickableText(
                text = "Log out",
                textColor = Color.Blue,
                onClick = {}
            )
            ProfileDivider()
            ProfileClickableText(
                text = "Delete account",
                textColor = Color.Red,
                onClick = {}
            )
        }
    }
}