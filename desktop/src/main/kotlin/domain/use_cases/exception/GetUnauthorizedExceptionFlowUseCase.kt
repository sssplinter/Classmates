package domain.use_cases.exception

import domain.exceptions_broadscast.ConnectionExceptionsBroadcast

class GetUnauthorizedExceptionFlowUseCase(
    private val connectionExceptionsBroadcast: ConnectionExceptionsBroadcast,
) {
    operator fun invoke() = connectionExceptionsBroadcast.unauthorizedException
}