package com.breaktime.classmates.di.modules

import com.breaktime.classmates.App
import com.breaktime.classmates.domain.source.ApiClient
import com.breaktime.classmates.domain.source.auth.AuthRepository
import com.breaktime.classmates.domain.source.auth.local.AuthPreferences
import com.breaktime.classmates.domain.source.auth.local.AuthPreferencesImpl
import com.breaktime.classmates.domain.source.auth.remote.AuthApiService
import com.breaktime.classmates.domain.source.chat.ChatRepository
import com.breaktime.classmates.domain.source.chat.local.ChatStorage
import com.breaktime.classmates.domain.source.chat.local.ChatStorageImpl
import com.breaktime.classmates.domain.source.chat.remote.ChatApiService
import com.breaktime.classmates.domain.source.people.PeopleRepository
import com.breaktime.classmates.domain.source.people.local.PeopleStorage
import com.breaktime.classmates.domain.source.people.local.PeopleStorageImpl
import com.breaktime.classmates.domain.source.people.remote.PeopleApiService
import com.breaktime.classmates.domain.source.settings.SettingsRepository
import com.breaktime.classmates.domain.source.settings.local.SettingsStorage
import com.breaktime.classmates.domain.source.settings.local.SettingsStorageImpl
import com.breaktime.classmates.domain.source.user.UserRepository
import com.breaktime.classmates.domain.source.user.remote.UserApiService
import com.breaktime.classmates.util.BASE_URL
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val repositoryModule = DI.Module(name = "repository", allowSilentOverride = false) {
    bind<AuthPreferences>() with singleton { AuthPreferencesImpl(App.instance.applicationContext) }
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

    bind<SettingsStorage>() with singleton { SettingsStorageImpl(App.instance.applicationContext) }
    bind { singleton { SettingsRepository(instance()) } }
}