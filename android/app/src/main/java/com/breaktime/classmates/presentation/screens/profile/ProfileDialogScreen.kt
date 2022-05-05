package com.breaktime.classmates.presentation.screens.profile

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
import androidx.navigation.NavHostController
import com.breaktime.classmates.R
import com.breaktime.classmates.presentation.components.RowUniversityItem
import com.breaktime.classmates.presentation.components.dialogs.ShadowBox
import com.breaktime.classmates.presentation.screens.profile.elements.*
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING
import com.breaktime.classmates.ui.theme.MEDIUM_PADDING
import com.breaktime.classmates.ui.theme.SMALL_PADDING
import org.kodein.di.compose.rememberInstance

import com.breaktime.classmates.presentation.screens.profile.elements.ProfileDivider as ProfileDivider1

@Composable
fun ProfileScreen(navController: NavHostController, onOutBoxClick: () -> Unit) {
    val viewModel: ProfileScreenViewModel by rememberInstance()

    // TODO: 14.04.22 add profile entity, which will provide full name and other info
    val name = remember { mutableStateOf("name") }
    val surname = remember { mutableStateOf("surname") }
    val patronymic = remember { mutableStateOf("patronymic") }
    val bio = remember { mutableStateOf("bio") }
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
    val showNoInternetConnectionDialog = remember { mutableStateOf(false) }
    val showLoadingDialog = remember { mutableStateOf(false) }

    initProfileObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        navController = navController,
        showNoInternetConnectionDialog = showNoInternetConnectionDialog,
        showLoadingDialog = showLoadingDialog
    )

    ShadowBox(
        onClick = {
            viewModel.setEvent(ProfileScreenContract.Event.OnDialogClose)
        }
    ){}

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
                image = R.drawable.no_user_photo,
                onClick = {}
            )
            Column(modifier = Modifier.padding(start = EXTRA_SMALL_PADDING)) {
                ProfileTextField(text = name)
                ProfileDivider1()
                ProfileTextField(text = surname)
                ProfileDivider1()
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
                            viewModel.setEvent(
                                ProfileScreenContract.Event.OnEditUniversityBtnClick(
                                    1
                                )
                            )
                        }
                    )
                    if (index < universitiesList.value.lastIndex)
                        ProfileDivider1()
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
                onClick = {
                    viewModel.setEvent(ProfileScreenContract.Event.OnLogOutBtnClick)
                }
            )
            ProfileDivider1()
            ProfileClickableText(
                text = "Delete account",
                textColor = Color.Red,
                onClick = {
                    viewModel.setEvent(ProfileScreenContract.Event.OnDeleteAccountBtnClick)
                }
            )
        }
    }
}

