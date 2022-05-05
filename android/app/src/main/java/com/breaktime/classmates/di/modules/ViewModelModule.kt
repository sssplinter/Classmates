package com.breaktime.classmates.di.modules

import com.breaktime.classmates.domain.entities.data.ChatInfo
import com.breaktime.classmates.presentation.screens.chat.ChatScreenViewModel
import com.breaktime.classmates.presentation.screens.friends.FriendsScreenViewModel
import com.breaktime.classmates.presentation.screens.host.HostScreenViewModel
import com.breaktime.classmates.presentation.screens.login.LoginScreenViewModel
import com.breaktime.classmates.presentation.screens.message.MessagesScreenViewModel
import com.breaktime.classmates.presentation.screens.people.PeopleScreenViewModel
import com.breaktime.classmates.presentation.screens.profile.ProfileScreenViewModel
import com.breaktime.classmates.presentation.screens.settings.SettingsScreenViewModel
import com.breaktime.classmates.presentation.screens.splash.SplashScreenViewModel
import org.kodein.di.*

val viewModelModule = DI.Module(name = "viewModelModule", allowSilentOverride = false) {
    bind { singleton { HostScreenViewModel(instance(), instance(), instance(), instance()) } }
    bind { singleton { SplashScreenViewModel(instance(), instance(), instance(), instance(), instance(), instance(), instance()) } }
    bind { singleton { LoginScreenViewModel(instance(), instance(), instance(), instance(), instance()) } }
    bind { singleton { ProfileScreenViewModel(instance(), instance(), instance()) } }
    bind { singleton { MessagesScreenViewModel(instance(), instance(), instance(), instance(), instance()) } }
    bind { singleton { PeopleScreenViewModel(instance(), instance(), instance(), instance(), instance(), instance(), instance()) } }
    bind { singleton { FriendsScreenViewModel(instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance()) } }
    bind { singleton { SettingsScreenViewModel(instance(), instance(),instance(), instance()) } }
    bind { multiton { chatInfo: ChatInfo -> ChatScreenViewModel(chatInfo, instance(),instance(), instance()) } }
}