package di.modules

import domain.use_cases.authorization.*
import domain.use_cases.chat.*
import domain.use_cases.user.LoadUserInfoUseCase
import domain.use_cases.user.SetUserFullNameUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val useCaseModule = DI.Module(name = "useCase", allowSilentOverride = false) {
    bind { singleton { CheckAuthorizationUseCase(instance()) } }
    bind { singleton { SignInUseCase(instance()) } }
    bind { singleton { SaveAuthorizationUseCase(instance()) } }
    bind { singleton { SignUpUseCase(instance()) } }
    bind { singleton { SignOutUseCase(instance()) } }
    bind { singleton { DeleteAccountUseCase(instance()) } }
    bind { singleton { CopyMessageToClipboardTextUseCase(instance()) } }
    bind { singleton { DeleteMessageUseCase(instance()) } }
    bind { singleton { FindChatsUseCase(instance()) } }
    bind { singleton { FindMessagesUseCase(instance()) } }
    bind { singleton { GetChatMessagesUseCase(instance()) } }
    bind { singleton { SendMessageUseCase(instance()) } }
    bind { singleton { LoadUserInfoUseCase(instance()) } }
    bind { singleton { SetUserFullNameUseCase(instance()) } }
}