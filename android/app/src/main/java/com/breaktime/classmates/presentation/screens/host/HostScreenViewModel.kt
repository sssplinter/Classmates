package com.breaktime.classmates.presentation.screens.host

import com.breaktime.classmates.domain.use_cases.exception.GetBadInputExceptionFlowUseCase
import com.breaktime.classmates.domain.use_cases.exception.GetForbiddenExceptionFlowUseCase
import com.breaktime.classmates.domain.use_cases.exception.GetNoConnectionExceptionFlowUseCase
import com.breaktime.classmates.domain.use_cases.exception.GetUnauthorizedExceptionFlowUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.breaktime.classmates.presentation.base.BaseViewModel

class HostScreenViewModel(
    private val getBadInputExceptionFlowUseCase: GetBadInputExceptionFlowUseCase,
    private val getForbiddenExceptionFlowUseCase: GetForbiddenExceptionFlowUseCase,
    private val getNoConnectionExceptionFlowUseCase: GetNoConnectionExceptionFlowUseCase,
    private val getUnauthorizedExceptionFlowUseCase: GetUnauthorizedExceptionFlowUseCase,
) : BaseViewModel<HostScreenContract.Event, HostScreenContract.State, HostScreenContract.Effect>() {

    init {
        startCollectingBadInputException()
        startCollectingForbiddenException()
        startCollectingNoConnectionException()
        startCollectingUnauthorizedException()
    }

    private fun startCollectingBadInputException() = MainScope().launch {
        getBadInputExceptionFlowUseCase.invoke().collect {
            if (it != null) setEffect { HostScreenContract.Effect.ShowBadInputDialog }
        }
    }

    private fun startCollectingForbiddenException() = MainScope().launch {
        getForbiddenExceptionFlowUseCase.invoke().collect {
            if (it != null) setEffect { HostScreenContract.Effect.ShowForbiddenDialog }
        }
    }

    private fun startCollectingNoConnectionException() = MainScope().launch {
        getNoConnectionExceptionFlowUseCase.invoke().collect {
            val effect = if (it != null) HostScreenContract.Effect.ShowNoConnectionDialog
            else HostScreenContract.Effect.HideNoConnectionDialog
            setEffect { effect }
        }
    }

    private fun startCollectingUnauthorizedException() = MainScope().launch {
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