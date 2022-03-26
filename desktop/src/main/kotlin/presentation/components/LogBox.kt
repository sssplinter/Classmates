package presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.*
import util.isEmail

@Composable
fun LogBox() {
    var isSignIn by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var isWrongEmailError by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var isPasswordError by remember { mutableStateOf(false) }
    var isRepeatPasswordError by remember { mutableStateOf(false) }
    var repeatPassword by remember { mutableStateOf("") }
    val scale = animateFloatAsState(if (!isSignIn) 1f else 0f)
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.height(50.dp),
                    painter = painterResource("owl.png"),
                    contentDescription = "email icon"
                )
                Text(
                    text = "Login",
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
                        text = "Sign in"
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
                        text = "Sign up"
                    )
                }
            }

            Column {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(vertical = EXTRA_SMALL_PADDING),
                    value = email,
                    isError = isWrongEmailError,
                    onValueChange = { email = it },
                    label = { Text("email") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "email icon"
                        )
                    },
                    trailingIcon = {
                        if (isWrongEmailError)
                            Icon(
                                imageVector = Icons.Default.Warning,
                                "mail error",
                                tint = MaterialTheme.colors.error
                            )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                if (isWrongEmailError) {
                    Text(
                        text = "Wrong email",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = MEDIUM_PADDING)
                    )
                }
            }

            Column {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(vertical = EXTRA_SMALL_PADDING),
                    value = password,
                    isError = isPasswordError,
                    onValueChange = { password = it },
                    label = { Text("password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "password icon"
                        )
                    },
                    trailingIcon = {
                        if (isPasswordError)
                            Icon(
                                imageVector = Icons.Default.Warning,
                                "error",
                                tint = MaterialTheme.colors.error
                            )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )
                if (isPasswordError)
                    Text(
                        text = "Password is empty",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = MEDIUM_PADDING)
                    )
            }

            if (scale.value != 0f) {
                Column {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((70 * scale.value).dp)
                            .padding(vertical = EXTRA_SMALL_PADDING),
                        value = repeatPassword,
                        isError = isRepeatPasswordError,
                        onValueChange = { repeatPassword = it },
                        label = { Text("repeat password") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "password icon"
                            )
                        },
                        trailingIcon = {
                            if (isRepeatPasswordError)
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    "error",
                                    tint = MaterialTheme.colors.error
                                )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    if (isRepeatPasswordError)
                        Text(
                            text = "Password is empty",
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = MEDIUM_PADDING)
                        )
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SMALL_PADDING),
                onClick = {
                    isWrongEmailError = !email.isEmail()
                    isPasswordError = password.isEmpty()
                    if (!isSignIn)
                        isRepeatPasswordError = repeatPassword.isEmpty()
                    if (!isWrongEmailError && !isPasswordError) {
                        when {
                            isSignIn -> {
                                println("sign in")
                            }
                            !isRepeatPasswordError -> {
                                println("sign up")
                            }
                        }
                    }
                }
            ) {

                Text(text = if (isSignIn) "Sign in" else "Sign up")
            }
        }
    }
}

@Preview
@Composable
fun LogBoxPreview() {
    LogBox()
}