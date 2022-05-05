package presentation.screens.people_screen

import androidx.compose.runtime.MutableState
import domain.entities.data.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun initPeopleObservable(
    scope: CoroutineScope,
    viewModel: PeopleScreenViewModel,
    userToSendMessage: MutableState<UserInfo?>,
) {
    scope.launch {
        viewModel.effect.collect {
            scope.ensureActive()
            when (it) {
                is PeopleScreenContract.Effect.ShowSendMessageDialog -> {
                    userToSendMessage.value = it.user
                }
            }
        }
    }
}