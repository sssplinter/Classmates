package presentation.screens.profile_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.window.AwtWindow
import domain.entities.data.CurrentUser
import localization.Vocabulary
import navigation.component.NavHostController
import org.kodein.di.compose.rememberInstance
import presentation.components.dialogs.LoadingDialog
import presentation.components.dialogs.Popup
import presentation.screens.profile_dialog.elements.*
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.SMALL_PADDING
import util.BASE_URL
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.io.FilenameFilter

@Composable
fun ProfileScreen(navController: NavHostController, onOutBoxClick: () -> Unit) {
    val viewModel: ProfileScreenViewModel by rememberInstance()

    val name = remember { mutableStateOf(CurrentUser.name) }
    val surname = remember { mutableStateOf(CurrentUser.surname) }
    val bio = remember { mutableStateOf(CurrentUser.bio) }
    val showLoadingDialog = remember { mutableStateOf(false) }
    val isFileChooser = remember { mutableStateOf(false) }

    initProfileObservable(
        scope = rememberCoroutineScope(),
        viewModel = viewModel,
        navController = navController,
        showLoadingDialog = showLoadingDialog
    )

    Popup(
        onOutBoxClick = {
            onOutBoxClick()
            viewModel.setEvent(ProfileScreenContract.Event.OnDialogClose(name.value, surname.value, bio.value))
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
                    imageUrl = CurrentUser.profileImageUrl,
                    onClick = {
                        isFileChooser.value = true
                    }
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
                text = Vocabulary.localization.email,
                fontSize = 14.sp
            )
            ProfileColumnContainer(backgroundColor = Color(0xFFEFEFEF)) {
                ProfileReadOnlyFiled(CurrentUser.email)
            }
            ProfileLogoutButton { viewModel.setEvent(ProfileScreenContract.Event.OnLogOutBtnClick) }
        }
    }

    if (showLoadingDialog.value) {
        LoadingDialog()
    }

    if (isFileChooser.value) {
        AwtWindow(
            create = {
                val parent: Frame? = null
                val fileChooser = object : FileDialog(parent, "Choose a file", LOAD) {
                    override fun setVisible(value: Boolean) {
                        super.setVisible(value)
                        val file = file
                        val directory = directory
                        viewModel.setEvent(ProfileScreenContract.Event.UpdateProfileImage(directory + file))
                        isFileChooser.value = false
                    }
                }
                val okFileExtensions = listOf("jpg", "jpeg", "png", "gif")
                val fileNameFilter = object : FilenameFilter {
                    override fun accept(pathname: File?, name: String?): Boolean {
                        for (extension in okFileExtensions) {
                            if (name?.toLowerCase()?.endsWith(extension) == true) {
                                return true
                            }
                        }
                        return false
                    }
                }
                fileChooser.filenameFilter = fileNameFilter
                fileChooser
            },
            dispose = FileDialog::dispose
        )
    }
}