package domain.use_cases.authorization

sealed class UseCaseAuthResult {
    data class Authorized(val token: String) : UseCaseAuthResult()
    object UnAuthorized : UseCaseAuthResult()
    object NoSuchAccount : UseCaseAuthResult()
}