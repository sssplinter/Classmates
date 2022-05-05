package di.modules

import domain.source.ApiClient
import domain.source.auth.AuthRepository
import domain.source.auth.local.AuthPreferences
import domain.source.auth.local.AuthPreferencesImpl
import domain.source.auth.remote.AuthApiService
import domain.source.chat.ChatRepository
import domain.source.chat.local.ChatStorage
import domain.source.chat.local.ChatStorageImpl
import domain.source.chat.remote.ChatApiService
import domain.source.people.PeopleRepository
import domain.source.people.local.PeopleStorage
import domain.source.people.local.PeopleStorageImpl
import domain.source.people.remote.PeopleApiService
import domain.source.settings.SettingsRepository
import domain.source.settings.local.SettingsStorage
import domain.source.settings.local.SettingsStorageImpl
import domain.source.user.UserRepository
import domain.source.user.remote.UserApiService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import util.BASE_URL

val repositoryModule = DI.Module(name = "repository", allowSilentOverride = false) {
    bind<AuthPreferences>() with singleton { AuthPreferencesImpl() }
    bind<AuthApiService>() with singleton { ApiClient.create(BASE_URL) }
    bind { singleton { AuthRepository(instance(), instance()) } }

    bind<ChatStorage>() with singleton { ChatStorageImpl() }
    bind<ChatApiService>() with singleton { ApiClient.create(BASE_URL) }
    bind { singleton { ChatRepository(instance(), instance()) } }

    bind<PeopleStorage>() with singleton { PeopleStorageImpl() }
    bind<PeopleApiService>() with singleton { ApiClient.create(BASE_URL) }
    bind { singleton { PeopleRepository(instance(), instance()) } }

    bind<UserApiService>() with singleton { ApiClient.create(BASE_URL) }
    bind { singleton { UserRepository(instance()) } }

    bind<SettingsStorage>() with singleton { SettingsStorageImpl() }
    bind { singleton { SettingsRepository(instance()) } }
}