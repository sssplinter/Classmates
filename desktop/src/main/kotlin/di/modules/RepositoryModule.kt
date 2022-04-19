package di.modules

import domain.source.ApiClient
import domain.source.auth.AuthRepository
import domain.source.auth.local.AuthPreferences
import domain.source.auth.local.AuthPreferencesImpl
import domain.source.auth.remote.AuthApiService
import domain.source.chat.remote.ChatApiService
import domain.source.chat.ChatRepository
import domain.source.chat.local.ChatStorage
import domain.source.chat.local.ChatStoragesImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import util.BASE_URL

val repositoryModule = DI.Module(name = "repository", allowSilentOverride = false) {
    bind<AuthPreferences>() with singleton { AuthPreferencesImpl() }
    bind<AuthApiService>() with singleton { ApiClient.create(BASE_URL) }
    bind { singleton { AuthRepository(instance(), instance()) } }

    bind<ChatStorage>() with singleton { ChatStoragesImpl() }
    bind<ChatApiService>() with singleton { ApiClient.create(BASE_URL) }
    bind { singleton { ChatRepository(instance(), instance()) } }
}