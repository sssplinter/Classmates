package localization.languages

import localization.Language

object LanguageEn : Language {
    override val appName: String
        get() = "Classmates"
    override val login: String
        get() = "Login"
    override val signIn: String
        get() = "Sign in"
    override val signUp: String
        get() = "Sign up"
    override val email: String
        get() = "email"
    override val password: String
        get() = "password"
    override val repeatPassword: String
        get() = "repeat password"
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