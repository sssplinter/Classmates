package presentation.screens.login_screen

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
import kotlinx.coroutines.delay
import localization.Language
import localization.Vocabulary
import presentation.components.TextFieldWithError
import ui.theme.*
import util.isEmail

@Composable
fun LogBox(
    onLogIn: (email: String, password: String) -> Unit,
    onSignUp: (email: String, password: String) -> Unit,
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
                    painter = painterResource("owl.svg"),
                    contentDescription = "logo icon"
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
                        isWrongEmailError = false
                        isPasswordError = false
                        isRepeatPasswordError = false
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = EXTRA_LARGE_PADDING),
                        text = Vocabulary.localization.signIn
                    )
                }
                TextButton(
                    colors = if (!isSignIn) ButtonDefaults.textButtonColors(backgroundColor = MaterialTheme.colors.loginActiveButton) else ButtonDefaults.textButtonColors(),
                    onClick = {
                        isSignIn = false
                        isWrongEmailError = false
                        isPasswordError = false
                        isRepeatPasswordError = false
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = EXTRA_LARGE_PADDING),
                        text = Vocabulary.localization.signUp
                    )
                }
            }

            TextFieldWithError(
                value = email,
                onValueChange = { email = it },
                label = Vocabulary.localization.email,
                labelIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "email icon"
                    )
                },
                errorText = emailError,
                isError = isWrongEmailError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            TextFieldWithError(
                value = password,
                onValueChange = { password = it },
                label = Vocabulary.localization.password,
                labelIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "password icon"
                    )
                },
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
                    labelIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "password icon"
                        )
                    },
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

                    if (!isWrongEmailError && !isPasswordError) {
                        when {
                            isSignIn -> {
                                onLogIn(email, password)
                            }
                            !isRepeatPasswordError -> {
                                onSignUp(email, password)
                            }
                        }
                    }
                }
            ) {
                Text(text = if (isSignIn) Vocabulary.localization.signIn else Vocabulary.localization.signUp)
            }
        }
    }
}

private fun checkEmailError(language: Language, email: String) = when {
    email.isEmpty() -> language.emptyEmailError
    !email.isEmail() -> language.wrongEmailError
    else -> language.emptyText
}

private fun checkPasswordError(language: Language, password: String) = when {
    password.isEmpty() -> language.emptyPasswordError
    password.length < 8 -> language.wrongPasswordLengthError
    else -> language.emptyText
}

private fun checkSecondPasswordError(language: Language, firstPassword: String, secondPassword: String) = when {
    secondPassword.isEmpty() -> language.emptyPasswordError
    secondPassword.length < 8 -> language.wrongPasswordLengthError
    firstPassword != secondPassword -> language.differentPasswordError
    else -> language.emptyText
}