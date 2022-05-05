package com.breaktime.classmates.localization.languages

import com.breaktime.classmates.localization.Language

object LanguageRu : Language {
    override val appName: String
        get() = "Одногруппники"
    override val login: String
        get() = "Логин"
    override val signIn: String
        get() = "Регистрация"
    override val signUp: String
        get() = "Войти"
    override val email: String
        get() = "email"
    override val password: String
        get() = "пароль"
    override val repeatPassword: String
        get() = "повторите пароль"
    override val emptyEmailError: String
        get() = "введите email"
    override val wrongEmailError: String
        get() = "неверный email"
    override val emptyPasswordError: String
        get() = "введите пароль"
    override val wrongPasswordLengthError: String
        get() = "пароль должен содержать не менее 8 символов"
    override val differentPasswordError: String
        get() = "пароли не совпадают"
}