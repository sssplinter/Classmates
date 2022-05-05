package com.breaktime.classmates.localization.languages

import com.breaktime.classmates.localization.Language

object LanguageFr : Language {
    override val appName: String
        get() = "Camarades"
    override val login: String
        get() = "Login"
    override val signIn: String
        get() = "Se connecte"
    override val signUp: String
        get() = "Enregistrer"
    override val email: String
        get() = "email"
    override val password: String
        get() = "password"
    override val repeatPassword: String
        get() = "confirm password"
    override val emptyEmailError: String
        get() =  "email is empty"
    override val wrongEmailError: String
        get() = "wrong email"
    override val emptyPasswordError: String
        get() = "password is empty"
    override val wrongPasswordLengthError: String
        get() = "password less then 8 signs"
    override val differentPasswordError: String
        get() = "passwords are different"
}