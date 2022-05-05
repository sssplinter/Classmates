package presentation.screens.main_screen

import domain.use_cases.chat_flow_data.NewMessageFlowNotifierUseCase
import domain.use_cases.people_flow_data.NewConnectionFlowNotifierUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import presentation.base.BaseViewModel

class MainScreenViewModel(
    private val newMessageFlowNotifierUseCase: NewMessageFlowNotifierUseCase,
    private val newConnectionFlowNotifierUseCase: NewConnectionFlowNotifierUseCase,
) :
    BaseViewModel<MainScreenContract.Event, MainScreenContract.State, MainScreenContract.Effect>() {

    init {
        subscribeOnMessagesNotifications()
        subscribeOnConnectionsNotifications()
    }

    private fun subscribeOnMessagesNotifications() = MainScope().launch {
        newMessageFlowNotifierUseCase().collect {
            setEffect { MainScreenContract.Effect.NotifyMessages }
        }
    }

    private fun subscribeOnConnectionsNotifications() = MainScope().launch {
        newConnectionFlowNotifierUseCase().collect {
            setEffect { MainScreenContract.Effect.NotifyConnections }
        }
    }

    override fun createInitialState(): MainScreenContract.State {
        return MainScreenContract.State(MainScreenContract.MainScreenState.Idle)
    }

    override fun handleEvent(event: MainScreenContract.Event) {
        when (event) {
            is MainScreenContract.Event.OnMenuButtonClick ->
                setEffect { MainScreenContract.Effect.ChangeFragment(event.route) }
            is MainScreenContract.Event.OnProfileButtonClick ->
                setEffect { MainScreenContract.Effect.OpenProfile }
            is MainScreenContract.Event.OnCloseProfileClick ->
                setEffect { MainScreenContract.Effect.CloseProfile }
        }
    }

    override fun clearState() {
        setState { copy(state = MainScreenContract.MainScreenState.Idle) }
    }
}