package di.modules

import domain.use_cases.authorization.*
import domain.use_cases.chat.*
import domain.use_cases.chat_flow_data.GetChatMessagesFlowUseCase
import domain.use_cases.chat_flow_data.GetChatsFlowUseCase
import domain.use_cases.chat_flow_data.NewMessageFlowNotifierUseCase
import domain.use_cases.exception.GetBadInputExceptionFlowUseCase
import domain.use_cases.exception.GetForbiddenExceptionFlowUseCase
import domain.use_cases.exception.GetNoConnectionExceptionFlowUseCase
import domain.use_cases.exception.GetUnauthorizedExceptionFlowUseCase
import domain.use_cases.friendship.*
import domain.use_cases.people.GetFriendsUseCase
import domain.use_cases.people_flow_data.*
import domain.use_cases.server_connection.StartCheckConnectionWithServerUseCase
import domain.use_cases.server_connection.StopCheckConnectionWithServerUseCase
import domain.use_cases.settings.GetLanguageUseCase
import domain.use_cases.settings.GetThemeUseCase
import domain.use_cases.settings.SaveLanguageUseCase
import domain.use_cases.settings.SaveThemeUseCase
import domain.use_cases.user_info.LoadProfileInfoUseCase
import domain.use_cases.user_info.UpdateProfileInfoUseCase
import domain.use_cases.user_info.UploadProfileImageUseCase
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
    bind { singleton { SendPrivateMessageUseCase(instance()) } }
    bind { singleton { LoadProfileInfoUseCase(instance(), instance()) } }
    bind { singleton { SetUserFullNameUseCase(instance()) } }
    bind { singleton { GetBadInputExceptionFlowUseCase(instance()) } }
    bind { singleton { GetForbiddenExceptionFlowUseCase(instance()) } }
    bind { singleton { GetNoConnectionExceptionFlowUseCase(instance()) } }
    bind { singleton { GetUnauthorizedExceptionFlowUseCase(instance()) } }
    bind { singleton { StartCheckConnectionWithServerUseCase(instance()) } }
    bind { singleton { StopCheckConnectionWithServerUseCase(instance()) } }
    bind { singleton { AllUsersFlowUseCase(instance()) } }
    bind { singleton { FriendsFlowUseCase(instance()) } }
    bind { singleton { SubscribersFlowUseCase(instance()) } }
    bind { singleton { SubscriptionsFlowUseCase(instance()) } }
    bind { singleton { ApproveFriendRequestUseCase(instance()) } }
    bind { singleton { RejectFriendRequestUseCase(instance()) } }
    bind { singleton { RemoveFriendUseCase(instance()) } }
    bind { singleton { RemoveSubscriptionUseCase(instance()) } }
    bind { singleton { SendFriendRequestUseCase(instance()) } }
    bind { singleton { GetChatsFlowUseCase(instance()) } }
    bind { singleton { GetChatMessagesFlowUseCase(instance()) } }
    bind { singleton { NewMessageFlowNotifierUseCase(instance()) } }
    bind { singleton { NewConnectionFlowNotifierUseCase(instance()) } }
    bind { singleton { SaveThemeUseCase(instance()) } }
    bind { singleton { GetThemeUseCase(instance()) } }
    bind { singleton { SaveLanguageUseCase(instance()) } }
    bind { singleton { GetLanguageUseCase(instance()) } }
    bind { singleton { UpdateProfileInfoUseCase(instance(), instance()) } }
    bind { singleton { CreateGroupChatUseCase(instance()) } }
    bind { singleton { GetFriendsUseCase(instance()) } }
    bind { singleton { UploadProfileImageUseCase(instance()) } }
}