package di.modules

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import presentation.screens.login_screen.LoginScreenViewModel
import presentation.screens.profile_dialog.ProfileScreenViewModel
import presentation.screens.splash_screen.SplashScreenViewModel

val viewModelModule = DI.Module(name = "viewModelModule", allowSilentOverride = false) {
    bind { singleton { SplashScreenViewModel(instance()) } }
    bind { singleton { LoginScreenViewModel(instance(), instance()) } }
    bind { singleton { ProfileScreenViewModel(instance(), instance()) } }
}