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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.entities.data.CurrentUser
import navigation.component.NavHostController
import org.kodein.di.compose.rememberInstance
import presentation.components.RowUniversityItem
import presentation.components.dialogs.LoadingDialog
import presentation.components.dialogs.Popup
import presentation.screens.profile_dialog.elements.*
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.SMALL_PADDING

@Composable
fun ProfileScreen(navController: NavHostController, onOutBoxClick: () -> Unit) {
    val viewModel: ProfileScreenViewModel by rememberInstance()

    val name = remember { mutableStateOf(CurrentUser.name) }
    val surname = remember { mutableStateOf(CurrentUser.surname) }
    val bio = remember { mutableStateOf(CurrentUser.bio) }
    val universitiesList = remember {
        mutableStateOf(
            listOf(
                Triple("BSUIR", "Poit", "951006"),
                Triple("BSU", "Math", "103id4"),
                Triple("BSU", "Math", "103id4"),
                Triple("BSU", "Math", "103id4"),
                Triple("BSU", "Math", "103id4"),
            )
        )
    }
    val showLoadingDialog = remember { mutableStateOf(false) }

    initProfileObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        navController = navController,
        showLoadingDialog = showLoadingDialog
    )

    Popup(
        onOutBoxClick = {
            onOutBoxClick()
            viewModel.setEvent(ProfileScreenContract.Event.OnDialogClose)
        })
    {
        Column(
            modifier = Modifier
                .width(350.dp)
                .clickable(enabled = false, onClick = {})
                .clip(MaterialTheme.shapes.medium)
                .background(Color.White)
                .padding(SMALL_PADDING)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileRowContainer(backgroundColor = Color(0xFFEFEFEF)) {
                ProfileImageButton(
                    imageUrl = "no_user_photo.svg",
                    onClick = {}
                )
                Column(modifier = Modifier.padding(start = EXTRA_SMALL_PADDING)) {
                    ProfileTextField(text = name)
                    ProfileDivider()
                    ProfileTextField(text = surname)
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
            ProfileCommentText(
                text = "Education",
                fontSize = 14.sp,
                imageVector = Icons.Default.Add,
                onImageClick = {
                    viewModel.setEvent(ProfileScreenContract.Event.OnAddUniversityBtnClick)
                }
            )
            ProfileColumnContainer(
                modifier = Modifier
                    .padding(bottom = SMALL_PADDING)
                    .heightIn(max = 140.dp),
                backgroundColor = Color(0xFFEFEFEF)
            ) {
                LazyColumn {
                    itemsIndexed(universitiesList.value) { index, (universityName, speciality, group) ->
                        RowUniversityItem(
                            universityName = universityName,
                            speciality = speciality,
                            group = group,
                            onEditClick = {
                                viewModel.setEvent(ProfileScreenContract.Event.OnEditUniversityBtnClick(1))
                            }
                        )
                        if (index < universitiesList.value.lastIndex)
                            ProfileDivider()
                    }
                }
            }
            ProfileLogoutButton { viewModel.setEvent(ProfileScreenContract.Event.OnLogOutBtnClick) }
        }
    }

    if (showLoadingDialog.value) {
        LoadingDialog()
    }
}