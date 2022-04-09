package di

import di.modules.useCaseModule
import di.modules.viewModelModule
import org.kodein.di.DI

val di = DI {
    importAll(viewModelModule, useCaseModule)
}