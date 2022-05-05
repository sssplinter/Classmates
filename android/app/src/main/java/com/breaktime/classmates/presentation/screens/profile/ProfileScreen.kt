package com.breaktime.classmates.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.breaktime.classmates.domain.entities.data.CurrentUser
import com.breaktime.classmates.localization.Vocabulary
import com.breaktime.classmates.presentation.components.dialogs.LoadingDialog
import com.breaktime.classmates.presentation.screens.profile.elements.*
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING
import com.breaktime.classmates.ui.theme.SMALL_PADDING
import org.kodein.di.compose.rememberInstance

@Composable
fun ProfileScreen(navController: NavHostController) {
    val viewModel: ProfileScreenViewModel by rememberInstance()

    val name = remember { mutableStateOf(CurrentUser.name) }
    val surname = remember { mutableStateOf(CurrentUser.surname) }
    val bio = remember { mutableStateOf(CurrentUser.bio) }
    val showLoadingDialog = remember { mutableStateOf(false) }

    initProfileObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        navController = navController,
        showLoadingDialog = showLoadingDialog,
        name = name,
        surname = surname,
        bio = bio
    )

    Column {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        ProfileTitle(text = "Profile")
                    },
                    navigationIcon = {
                        ProfileTitleTextButton(
                            modifier = Modifier.padding(start = SMALL_PADDING),
                            text = "back",
                            textColor = Color.Black,
                            onClick = { navController.popBackStack() }
                        )
                    },
                    actions = {
                        ProfileTitleTextButton(
                            modifier = Modifier.padding(end = SMALL_PADDING),
                            text = "save",
                            textColor = Color.Black,
                            onClick = {
                                viewModel.setEvent(
                                    ProfileScreenContract.Event.OnSaveInfo(
                                        name.value,
                                        surname.value,
                                        bio.value
                                    )
                                )
                                navController.popBackStack()
                            }
                        )
                    },
                    modifier = Modifier.shadow(5.dp),
                    elevation = 5.dp,
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(SMALL_PADDING),
                    horizontalAlignment = Alignment.CenterHorizontally,
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
                        text = Vocabulary.localization.enterYourName,
                        fontSize = 12.sp
                    )
                    Column(modifier = Modifier.padding(vertical = SMALL_PADDING)) {
                        ProfileCommentText(
                            text = Vocabulary.localization.bio,
                            fontSize = 14.sp
                        )
                        ProfileColumnContainer(backgroundColor = Color(0xFFEFEFEF)) {
                            ProfileTextField(text = bio)
                        }
                        ProfileCommentText(
                            text = Vocabulary.localization.anyDetails,
                            fontSize = 12.sp
                        )
                    }
                    ProfileCommentText(
                        text = "Email",
                        fontSize = 14.sp
                    )
                    ProfileColumnContainer(backgroundColor = Color(0xFFEFEFEF)) {
                        ProfileReadOnlyFiled(CurrentUser.email)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    ProfileLogoutButton { viewModel.setEvent(ProfileScreenContract.Event.OnLogOutBtnClick) }
                    Spacer(modifier = Modifier.padding(bottom = EXTRA_SMALL_PADDING))
                }
            }
        )
    }
    if (showLoadingDialog.value) {
        LoadingDialog()
    }
}