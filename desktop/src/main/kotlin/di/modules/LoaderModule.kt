package di.modules

import domain.connection.ConnectionChecker
import domain.exceptions_broadscast.ConnectionExceptionsBroadcast
import domain.loader.AsyncChatLoader
import domain.loader.AsyncDataLoader
import domain.loader.AsyncPeopleLoader
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val loaderModule = DI.Module(name = "loader", allowSilentOverride = false) {
    bind { singleton { ConnectionExceptionsBroadcast() } }
    bind { singleton { ConnectionChecker(instance()) } }
    bind { singleton { AsyncDataLoader(instance()) } }
    bind { singleton { AsyncChatLoader(instance(), instance()) } }
    bind { singleton { AsyncPeopleLoader(instance(), instance()) } }
}