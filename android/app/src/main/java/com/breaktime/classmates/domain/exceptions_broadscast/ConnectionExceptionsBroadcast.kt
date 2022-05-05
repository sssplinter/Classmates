package com.breaktime.classmates.domain.exceptions_broadscast

import com.breaktime.classmates.domain.exceptions_broadscast.exceptions.BadInputException
import com.breaktime.classmates.domain.exceptions_broadscast.exceptions.ForbiddenException
import com.breaktime.classmates.domain.exceptions_broadscast.exceptions.NoConnectionException
import com.breaktime.classmates.domain.exceptions_broadscast.exceptions.UnauthorisedException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException

class ConnectionExceptionsBroadcast : CoroutineScope {
    override var coroutineContext = Dispatchers.IO + SupervisorJob()

    private val _unauthorizedException = MutableStateFlow<Any?>(null)
    val unauthorizedException: StateFlow<Any?> get() = _unauthorizedException.asStateFlow()

    private val _noConnectionException = MutableStateFlow<Any?>(null)
    val noConnectionException: StateFlow<Any?> get() = _noConnectionException.asStateFlow()

    private val _forbiddenException = MutableStateFlow<Any?>(null)
    val forbiddenException: StateFlow<Any?> get() = _forbiddenException.asStateFlow()

    private val _badInputException = MutableStateFlow<Any?>(null)
    val badInputException: StateFlow<Any?> get() = _badInputException.asStateFlow()

    fun sendException(e: Exception?) = launch {
        when (e) {
            is BadInputException -> _badInputException.emit(e.javaClass)
            is ForbiddenException -> _forbiddenException.emit(e.javaClass)
            is UnauthorisedException -> _unauthorizedException.emit(e.javaClass)
            is NoConnectionException, is ConnectException -> _noConnectionException.emit(e.javaClass)
            null -> {
                _badInputException.emit(null)
                _forbiddenException.emit(null)
                _unauthorizedException.emit(null)
                _noConnectionException.emit(null)
            }
        }
    }
}