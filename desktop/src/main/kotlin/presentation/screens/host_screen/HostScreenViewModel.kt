package presentation.screens.host_screen

import domain.use_cases.exception.GetBadInputExceptionFlowUseCase
import domain.use_cases.exception.GetForbiddenExceptionFlowUseCase
import domain.use_cases.exception.GetNoConnectionExceptionFlowUseCase
import domain.use_cases.exception.GetUnauthorizedExceptionFlowUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel

class HostScreenViewModel(
    private val getBadInputExceptionFlowUseCase: GetBadInputExceptionFlowUseCase,
    private val getForbiddenExceptionFlowUseCase: GetForbiddenExceptionFlowUseCase,
    private val getNoConnectionExceptionFlowUseCase: GetNoConnectionExceptionFlowUseCase,
    private val getUnauthorizedExceptionFlowUseCase: GetUnauthorizedExceptionFlowUseCase,
) : BaseViewModel<HostScreenContract.Event, HostScreenContract.State, HostScreenContract.Effect>() {

    init {
        startCollectionBadInputException()
        startCollectionForbiddenException()
        startCollectionNoConnectionException()
        startCollectionUnauthorizedException()
    }

    private fun startCollectionBadInputException() = MainScope().launch {
        getBadInputExceptionFlowUseCase.invoke().collect {
            if (it != null) setEffect { HostScreenContract.Effect.ShowBadInputDialog }
        }
    }

    private fun startCollectionForbiddenException() = MainScope().launch {
        getForbiddenExceptionFlowUseCase.invoke().collect {
            if (it != null) setEffect { HostScreenContract.Effect.ShowForbiddenDialog }
        }
    }

    private fun startCollectionNoConnectionException() = MainScope().launch {
        getNoConnectionExceptionFlowUseCase.invoke().collect {
            val effect = if (it != null) HostScreenContract.Effect.ShowNoConnectionDialog
            else HostScreenContract.Effect.HideNoConnectionDialog
            setEffect { effect }
        }
    }

    private fun startCollectionUnauthorizedException() = MainScope().launch {
        getUnauthorizedExceptionFlowUseCase.invoke().collect {
            if (it != null)  setEffect { HostScreenContract.Effect.ShowNoAuthorizationDialog }
        }
    }

    override fun createInitialState(): HostScreenContract.State {
        return HostScreenContract.State(HostScreenContract.MainScreenState.Idle)
    }

    override fun handleEvent(event: HostScreenContract.Event) {
        when (event) {
            is HostScreenContract.Event.OnMessagesButtonClick -> {}
            is HostScreenContract.Event.OnConnectionsButtonClick -> {}
            is HostScreenContract.Event.OnSearchPeopleButtonClick -> {}
            is HostScreenContract.Event.OnSettingsButtonClick -> {}
            is HostScreenContract.Event.OnProfileButtonClick -> {}
        }
    }

    override fun clearState() {
        setState { copy(state = HostScreenContract.MainScreenState.Idle) }
    }
}