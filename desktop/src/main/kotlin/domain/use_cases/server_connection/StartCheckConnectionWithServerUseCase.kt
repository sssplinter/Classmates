package domain.use_cases.server_connection

import domain.connection.ConnectionChecker


class StartCheckConnectionWithServerUseCase(
    private val connectionChecker: ConnectionChecker,
) {
    operator fun invoke() {
        connectionChecker.startCheckingConnection()
    }
}