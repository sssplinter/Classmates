package domain.use_cases.exception

import domain.exceptions_broadscast.ConnectionExceptionsBroadcast

class GetForbiddenExceptionFlowUseCase(
    private val connectionExceptionsBroadcast: ConnectionExceptionsBroadcast,
) {
    operator fun invoke() = connectionExceptionsBroadcast.forbiddenException
}