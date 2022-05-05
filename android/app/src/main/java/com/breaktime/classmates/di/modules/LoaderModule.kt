package com.breaktime.classmates.di.modules

import com.breaktime.classmates.domain.connection.ConnectionChecker
import com.breaktime.classmates.domain.exceptions_broadscast.ConnectionExceptionsBroadcast
import com.breaktime.classmates.domain.loader.AsyncDataLoader
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val loaderModule = DI.Module(name = "loader", allowSilentOverride = false) {
    bind { singleton { ConnectionExceptionsBroadcast() } }
    bind { singleton { ConnectionChecker(instance()) } }
    bind { singleton { AsyncDataLoader(instance(), instance(), instance()) } }
}