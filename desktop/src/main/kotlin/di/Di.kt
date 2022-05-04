package di

import di.modules.loaderModule
import di.modules.repositoryModule
import di.modules.useCaseModule
import di.modules.viewModelModule
import org.kodein.di.DI

val kodein = DI {
    importAll(viewModelModule, useCaseModule, repositoryModule, loaderModule)
}