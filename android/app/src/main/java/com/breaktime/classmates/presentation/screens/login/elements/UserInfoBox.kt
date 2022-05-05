package com.breaktime.classmates.presentation.screens.login.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.breaktime.classmates.localization.Vocabulary
import com.breaktime.classmates.presentation.components.TextFieldWithError
import com.breaktime.classmates.ui.theme.LARGE_PADDING
import com.breaktime.classmates.ui.theme.SMALL_PADDING
import com.breaktime.classmates.ui.theme.loginBackground

@Composable
fun UserInfoBox(
    onConfirmClick: (String, String) -> Unit,
    onBackClick: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var isNameError by remember { mutableStateOf(false) }

    var surname by remember { mutableStateOf("") }
    var isSurnameError by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(350.dp)
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.loginBackground)
                .fillMaxWidth()
                .padding(LARGE_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    modifier = Modifier.height(50.dp).clickable(onClick = onBackClick),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = Vocabulary.localization.logoImgContent
                )

                Row(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        text = Vocabulary.localization.setupUserInfo,
                        modifier = Modifier
                            .padding(bottom = SMALL_PADDING)
                            .padding(start = SMALL_PADDING),
                        textAlign = TextAlign.Center,
                        fontSize = 36.sp
                    )
                }
            }
            TextFieldWithError(
                value = name,
                onValueChange = {
                    name = it
                    isNameError = name.isEmpty()
                },
                label = Vocabulary.localization.name,
                errorText = Vocabulary.localization.nameNotEmpty,
                isError = isNameError
            )


            TextFieldWithError(
                value = surname,
                onValueChange = {
                    surname = it
                    isSurnameError = surname.isEmpty()
                },
                label = Vocabulary.localization.surname,
                errorText = Vocabulary.localization.surnameNotEmpty,
                isError = isSurnameError
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SMALL_PADDING),
                onClick = {
                    isNameError = name.isEmpty()
                    isSurnameError = surname.isEmpty()
                    if (!isNameError && !isSurnameError) {
                        onConfirmClick(name, surname)
                    }
                }
            ) {
                Text(text = Vocabulary.localization.confirm)
            }
        }
    }
}