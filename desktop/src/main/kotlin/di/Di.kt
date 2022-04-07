package di

import di.modules.viewModelModule
import org.kodein.di.DI

val di = DI {
    import(viewModelModule)
}