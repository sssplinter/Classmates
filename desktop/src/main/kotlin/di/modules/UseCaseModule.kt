package di.modules

import domain.use_cases.authorization.CheckAuthorizationUseCase
import domain.use_cases.authorization.SignInUseCase
import domain.use_cases.authorization.SignUpUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val useCaseModule = DI.Module(name = "useCase", allowSilentOverride = false) {
    bind { singleton { CheckAuthorizationUseCase() } }
    bind { singleton { SignInUseCase() } }
    bind { singleton { SignUpUseCase() } }
}