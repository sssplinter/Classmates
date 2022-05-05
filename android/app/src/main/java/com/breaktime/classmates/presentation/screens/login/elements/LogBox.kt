package com.breaktime.classmates.presentation.screens.login.elements

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.breaktime.classmates.R
import kotlinx.coroutines.delay
import com.breaktime.classmates.localization.Vocabulary
import com.breaktime.classmates.presentation.components.TextFieldWithError
import com.breaktime.classmates.presentation.screens.login.LoginScreenContract
import com.breaktime.classmates.presentation.screens.login.LoginScreenViewModel
import com.breaktime.classmates.ui.theme.*
import com.breaktime.classmates.util.checkEmailError
import com.breaktime.classmates.util.checkPasswordError
import com.breaktime.classmates.util.checkSecondPasswordError

@Composable
fun LogBox(
    viewModel: LoginScreenViewModel,
    onLogin: (email: String, password: String) -> Unit,
) {
    var isSignIn by remember { mutableStateOf(true) }

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var isWrongEmailError by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var isPasswordError by remember { mutableStateOf(false) }

    var repeatPassword by remember { mutableStateOf("") }
    var repeatPasswordError by remember { mutableStateOf("") }
    var isRepeatPasswordError by remember { mutableStateOf(false) }

    var startCardAnimation by remember { mutableStateOf(false) }
    val cardAlpha = animateFloatAsState(
        targetValue = if (startCardAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )
    val scale = animateFloatAsState(if (!isSignIn) 1f else 0f)
    LaunchedEffect(true) {
        delay(200)
        startCardAnimation = true
    }
    Card(
        modifier = Modifier
            .width(350.dp)
            .wrapContentHeight()
            .alpha(cardAlpha.value),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.loginBackground)
                .fillMaxWidth()
                .padding(LARGE_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.height(50.dp),
                    painter = painterResource(R.drawable.owl),
                    contentDescription = Vocabulary.localization.logoImgContent
                )
                Text(
                    text = Vocabulary.localization.login,
                    modifier = Modifier
                        .padding(bottom = SMALL_PADDING)
                        .padding(start = SMALL_PADDING),
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    colors = if (isSignIn) ButtonDefaults.textButtonColors(backgroundColor = MaterialTheme.colors.loginActiveButton) else ButtonDefaults.textButtonColors(),
                    onClick = {
                        isSignIn = true
                        viewModel.handleEvent(LoginScreenContract.Event.OnSwitchToSignInClick)
                        isWrongEmailError = false
                        isPasswordError = false
                        isRepeatPasswordError = false
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = THE_LARGEST_PADDING),
                        text = Vocabulary.localization.signIn
                    )
                }
                TextButton(
                    colors = if (!isSignIn) ButtonDefaults.textButtonColors(backgroundColor = MaterialTheme.colors.loginActiveButton) else ButtonDefaults.textButtonColors(),
                    onClick = {
                        isSignIn = false
                        viewModel.handleEvent(LoginScreenContract.Event.OnSwitchToSignUpClick)
                        isWrongEmailError = false
                        isPasswordError = false
                        isRepeatPasswordError = false
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = THE_LARGEST_PADDING),
                        text = Vocabulary.localization.signUp
                    )
                }
            }

            TextFieldWithError(
                value = email,
                onValueChange = { email = it },
                label = Vocabulary.localization.email,
                imageVector = Icons.Default.Email,
                errorText = emailError,
                isError = isWrongEmailError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            TextFieldWithError(
                value = password,
                onValueChange = { password = it },
                label = Vocabulary.localization.password,
                imageVector = Icons.Default.Lock,
                errorText = passwordError,
                isError = isPasswordError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            if (scale.value > 0.1f) {
                TextFieldWithError(
                    modifier = Modifier.height(70.dp * scale.value),
                    value = repeatPassword,
                    onValueChange = { repeatPassword = it },
                    label = Vocabulary.localization.repeatPassword,
                    imageVector = Icons.Default.Lock,
                    errorText = repeatPasswordError,
                    isError = isRepeatPasswordError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )
            }

            val localization = Vocabulary.localization
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SMALL_PADDING),
                onClick = {
                    emailError = checkEmailError(localization, email)
                    isWrongEmailError = emailError.isNotEmpty()

                    passwordError = checkPasswordError(localization, password)
                    isPasswordError = passwordError.isNotEmpty()

                    if (!isSignIn) {
                        repeatPasswordError = checkSecondPasswordError(localization, password, repeatPassword)
                        isRepeatPasswordError = repeatPasswordError.isNotEmpty()
                    }

                    if (!isWrongEmailError && !isPasswordError && (isSignIn || !isRepeatPasswordError)) {
                        onLogin(email, password)
                    }
                }
            ) {
                Text(text = if (isSignIn) Vocabulary.localization.signIn else Vocabulary.localization.signUp)
            }
        }
    }
}