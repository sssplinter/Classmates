package com.breaktime.classmates.di.modules

import com.breaktime.classmates.presentation.screens.host.HostScreenViewModel
import com.breaktime.classmates.presentation.screens.login.LoginScreenViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
//import com.breaktime.classmates.presentation.screens.chats_screen.ChatsScreenViewModel
//import com.breaktime.classmates.presentation.screens.friends_screen.FriendsScreenViewModel
//import com.breaktime.classmates.presentation.screens.host_screen.HostScreenViewModel
//import com.breaktime.classmates.presentation.screens.menu.LoginScreenViewModel
//import com.breaktime.classmates.presentation.screens.main.MainScreenViewModel
import com.breaktime.classmates.presentation.screens.people.PeopleScreenViewModel
import com.breaktime.classmates.presentation.screens.profile.ProfileScreenViewModel
//import com.breaktime.classmates.presentation.screens.settings.SettingsScreenViewModel
import com.breaktime.classmates.presentation.screens.splash.SplashScreenViewModel

val viewModelModule = DI.Module(name = "viewModelModule", allowSilentOverride = false) {
    bind { singleton { HostScreenViewModel(instance(), instance(), instance(), instance()) } }
    bind { singleton { SplashScreenViewModel(instance(), instance(), instance(), instance(), instance(), instance(), instance()) } }
    bind { singleton { LoginScreenViewModel(instance(), instance(), instance(), instance(), instance()) } }
//    bind { singleton { ProfileScreenViewModel(instance(), instance(), instance()) } }
//    bind { singleton { ChatsScreenViewModel(instance(), instance(), instance(), instance(), instance()) } }
    bind { singleton { PeopleScreenViewModel(instance(), instance(), instance(), instance(), instance(), instance(), instance()) } }
//    bind { singleton { FriendsScreenViewModel(instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance()) } }
//    bind { singleton { MainScreenViewModel(instance(), instance()) } }
//    bind { singleton { SettingsScreenViewModel(instance(), instance(),instance(), instance()) } }
}