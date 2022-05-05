package com.breaktime.classmates.domain.use_cases.exception

import com.breaktime.classmates.domain.exceptions_broadscast.ConnectionExceptionsBroadcast

class GetNoConnectionExceptionFlowUseCase(
    private val connectionExceptionsBroadcast: ConnectionExceptionsBroadcast,
) {
    operator fun invoke() = connectionExceptionsBroadcast.noConnectionException
}