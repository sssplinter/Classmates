package di.modules

import domain.use_cases.authorization.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val useCaseModule = DI.Module(name = "useCase", allowSilentOverride = false) {
    bind { singleton { CheckAuthorizationUseCase() } }
    bind { singleton { SignInUseCase() } }
    bind { singleton { SignUpUseCase() } }
    bind { singleton { SignOutUseCase() } }
    bind { singleton { DeleteAccountUseCase() } }
}