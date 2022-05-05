package com.breaktime.classmates.localization

interface Language {
    val appName: String
    val emptyText: String get() = ""
    val login: String
    val signIn: String
    val signUp: String
    val email: String
    val password: String
    val repeatPassword: String
    val emptyEmailError: String
    val wrongEmailError: String
    val emptyPasswordError: String
    val wrongPasswordLengthError: String
    val differentPasswordError: String
}