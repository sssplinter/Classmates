package presentation.screens.main_screen

import presentation.base.BaseViewModel

class MainScreenViewModel :
    BaseViewModel<MainScreenContract.Event, MainScreenContract.State, MainScreenContract.Effect>() {

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