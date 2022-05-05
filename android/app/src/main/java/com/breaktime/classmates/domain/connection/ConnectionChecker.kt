package com.breaktime.classmates.domain.connection

import com.breaktime.classmates.util.BASE_URL
import com.breaktime.classmates.domain.exceptions_broadscast.ConnectionExceptionsBroadcast
import com.breaktime.classmates.domain.exceptions_broadscast.exceptions.NoConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

private const val CHECK_DELAY = 500L

class ConnectionChecker(
    private val exceptionsBroadcast: ConnectionExceptionsBroadcast,
) {
    private var isChecking = false

    fun startCheckingConnection() {
        if (!isChecking) {
            isChecking = true
            startChecking()
        }
    }

    fun stopChecking() {
        isChecking = false
    }

    private fun startChecking() = MainScope().launch(Dispatchers.IO) {
        while (isChecking) {
            try {
                val url = URL(BASE_URL)
                val connection = url.openConnection()
                connection.connect()
                exceptionsBroadcast.sendException(null)
            } catch (e: MalformedURLException) {
                exceptionsBroadcast.sendException(NoConnectionException())
            } catch (e: IOException) {
                exceptionsBroadcast.sendException(NoConnectionException())
            }
            delay(CHECK_DELAY)
        }
    }
}