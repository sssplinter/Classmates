package di.modules

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import presentation.screens.chats_screen.ChatsScreenViewModel
import presentation.screens.create_group_dialog.CreateGroupViewModel
import presentation.screens.friends_screen.FriendsScreenViewModel
import presentation.screens.host_screen.HostScreenViewModel
import presentation.screens.login_screen.LoginScreenViewModel
import presentation.screens.main_screen.MainScreenViewModel
import presentation.screens.people_screen.PeopleScreenViewModel
import presentation.screens.profile_dialog.ProfileScreenViewModel
import presentation.screens.settings_screen.SettingsScreenViewModel
import presentation.screens.splash_screen.SplashScreenViewModel

val viewModelModule = DI.Module(name = "viewModelModule", allowSilentOverride = false) {
    bind { singleton { HostScreenViewModel(instance(), instance(), instance(), instance()) } }
    bind { singleton { SplashScreenViewModel(instance(), instance(), instance(), instance(), instance(), instance(), instance()) } }
    bind { singleton { LoginScreenViewModel(instance(), instance(), instance(), instance(), instance()) } }
    bind { singleton { ProfileScreenViewModel(instance(), instance(), instance(), instance()) } }
    bind { singleton { ChatsScreenViewModel(instance(), instance(), instance(), instance(), instance()) } }
    bind { singleton { PeopleScreenViewModel(instance(), instance(), instance(), instance(), instance(), instance(), instance()) } }
    bind { singleton { FriendsScreenViewModel(instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance()) } }
    bind { singleton { MainScreenViewModel(instance(), instance()) } }
    bind { singleton { SettingsScreenViewModel(instance(), instance(),instance(), instance()) } }
    bind { singleton { CreateGroupViewModel(instance(), instance()) } }
}