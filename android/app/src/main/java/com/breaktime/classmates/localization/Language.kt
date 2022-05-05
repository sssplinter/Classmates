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
    val settings: String
    val searchMsg: String
    val msg: String
    val yourConnections: String
    val search: String
    val actionImgContent: String
    val errorImgContent: String
    val logoImgContent: String
    val emailImgContent: String
    val logOut: String
    val group: String
    val editGroup: String
    val setupUserInfo: String
    val name: String
    val nameNotEmpty: String
    val surname: String
    val surnameNotEmpty: String
    val confirm: String
    val friends: String
    val subscribers: String
    val subscriptions: String
    val noInternet: String
    val noInternetMsg: String
    val findAllPeople: String
    val bio: String
    val anyDetails: String
    val enterYourName: String
    val theme: String
    val language: String
    val sendMsg: String
    val privateMsgSend: String
    val message: String
    val cancel: String
    val send: String

}