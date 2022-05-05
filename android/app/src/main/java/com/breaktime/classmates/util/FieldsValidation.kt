package com.breaktime.classmates.util

import com.breaktime.classmates.localization.Language

fun checkEmailError(language: Language, email: String) = when {
    email.isEmpty() -> language.emptyEmailError
    !email.isEmail() -> language.wrongEmailError
    else -> language.emptyText
}

fun checkPasswordError(language: Language, password: String) = when {
    password.isEmpty() -> language.emptyPasswordError
    password.length < 8 -> language.wrongPasswordLengthError
    else -> language.emptyText
}

fun checkSecondPasswordError(language: Language, firstPassword: String, secondPassword: String) = when {
    secondPassword.isEmpty() -> language.emptyPasswordError
    secondPassword.length < 8 -> language.wrongPasswordLengthError
    firstPassword != secondPassword -> language.differentPasswordError
    else -> language.emptyText
}