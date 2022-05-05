package domain.use_cases.server_connection

import domain.connection.ConnectionChecker


class StopCheckConnectionWithServerUseCase(
    private val connectionChecker: ConnectionChecker,
) {
    operator fun invoke() {
        connectionChecker.stopChecking()
    }
}