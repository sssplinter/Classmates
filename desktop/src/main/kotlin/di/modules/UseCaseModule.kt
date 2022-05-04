package di.modules

import domain.use_cases.authorization.*
import domain.use_cases.chat.*
import domain.use_cases.user_info.LoadProfileInfoUseCase
import domain.use_cases.authorization.SetUserFullNameUseCase
import domain.use_cases.exception.GetBadInputExceptionFlowUseCase
import domain.use_cases.exception.GetForbiddenExceptionFlowUseCase
import domain.use_cases.exception.GetNoConnectionExceptionFlowUseCase
import domain.use_cases.exception.GetUnauthorizedExceptionFlowUseCase
import domain.use_cases.server_connection.StartCheckConnectionWithServerUseCase
import domain.use_cases.server_connection.StopCheckConnectionWithServerUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val useCaseModule = DI.Module(name = "useCase", allowSilentOverride = false) {
    bind { singleton { CheckAuthorizationUseCase(instance()) } }
    bind { singleton { SignInUseCase(instance(), instance()) } }
    bind { singleton { SaveAuthorizationUseCase(instance()) } }
    bind { singleton { SignUpUseCase(instance()) } }
    bind { singleton { SignOutUseCase(instance()) } }
    bind { singleton { DeleteAccountUseCase(instance()) } }
    bind { singleton { CopyMessageToClipboardTextUseCase(instance()) } }
    bind { singleton { FindChatsUseCase(instance()) } }
    bind { singleton { FindMessagesUseCase(instance()) } }
    bind { singleton { GetChatMessagesUseCase(instance()) } }
    bind { singleton { SendMessageUseCase(instance()) } }
    bind { singleton { LoadProfileInfoUseCase(instance(), instance()) } }
    bind { singleton { SetUserFullNameUseCase(instance()) } }
    bind { singleton { GetBadInputExceptionFlowUseCase(instance()) } }
    bind { singleton { GetForbiddenExceptionFlowUseCase(instance()) } }
    bind { singleton { GetNoConnectionExceptionFlowUseCase(instance()) } }
    bind { singleton { GetUnauthorizedExceptionFlowUseCase(instance()) } }
    bind { singleton { StartCheckConnectionWithServerUseCase(instance()) } }
    bind { singleton { StopCheckConnectionWithServerUseCase(instance()) } }
}