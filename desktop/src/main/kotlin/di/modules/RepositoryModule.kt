package di.modules

import domain.source.UserRepository
import domain.source.auth.local.AuthPreferences
import domain.source.auth.local.AuthPreferencesImpl
import domain.source.auth.remote.AuthApiClient
import domain.source.auth.remote.AuthApiService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import util.BASE_URL

val repositoryModule = DI.Module(name = "repository", allowSilentOverride = false) {
    bind<AuthPreferences>() with singleton { AuthPreferencesImpl() }
    bind<AuthApiService>() with singleton { AuthApiClient.create(BASE_URL) }
    bind { singleton { UserRepository(instance(), instance()) } }
}