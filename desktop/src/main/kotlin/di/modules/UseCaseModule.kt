package di.modules

import domain.use_cases.CheckAuthorizationUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val useCaseModule = DI.Module(name = "useCase", allowSilentOverride = false) {
    bind { singleton { CheckAuthorizationUseCase() } }
}