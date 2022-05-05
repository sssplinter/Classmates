package com.breaktime.classmates.domain.use_cases.server_connection

import com.breaktime.classmates.domain.connection.ConnectionChecker


class StartCheckConnectionWithServerUseCase(
    private val connectionChecker: ConnectionChecker,
) {
    operator fun invoke() {
        connectionChecker.startCheckingConnection()
    }
}