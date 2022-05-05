package com.breaktime.classmates.localization.languages

import com.breaktime.classmates.localization.Language

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
        get() = "email is empty"
    override val wrongEmailError: String
        get() = "wrong email"
    override val emptyPasswordError: String
        get() = "password is empty"
    override val wrongPasswordLengthError: String
        get() = "password less than 8 signs"
    override val differentPasswordError: String
        get() = "passwords are different"
    override val settings: String
        get() = "Settings"
    override val searchMsg: String
        get() = "Search message..."
    override val msg: String
        get() = "Message..."
    override val yourConnections: String
        get() = "Your connections"
    override val search: String
        get() = "Search"
    override val group: String
        get() = "Group: "
    override val logOut: String
        get() = "Log out"
    override val editGroup: String
        get() = "edit ->"
    override val setupUserInfo: String
        get() = "Setup user info"
    override val name: String
        get() = "Name"
    override val nameNotEmpty: String
        get() = "Name shouldn't be empty"
    override val surname: String
        get() = "Surname"
    override val surnameNotEmpty: String
        get() = "Surname shouldn't be empty"
    override val confirm: String
        get() = "Confirm"
    override val friends: String
        get() = "Friends"
    override val subscribers: String
        get() = "Subscribers"
    override val noInternet: String
        get() = "No internet connection"
    override val noInternetMsg: String
        get() = "Please reconnect to network"
    override val subscriptions: String
        get() = "Subscriptions"
    override val findAllPeople: String
        get() = "Find all people"
    override val actionImgContent: String
        get() = "Action image"
    override val errorImgContent: String
        get() = "Error image"
    override val logoImgContent: String
        get() = "Logo icon"
    override val emailImgContent: String
        get() = "Email icon"
    override val bio: String
        get() = "BIO"
    override val anyDetails: String
        get() = "Any details such as age, occupation or city"
    override val enterYourName: String
        get() = "Enter your name and add a profile photo"
    override val theme: String
        get() = "Theme"
    override val language: String
        get() = "Language"
    override val sendMsg: String
        get() = "Send message"
    override val privateMsgSend: String
        get() = "That message will be send to private messages"
    override val message: String
        get() = "message"
    override val cancel: String
        get() = "Cancel"
    override val send: String
        get() = "Send"
}