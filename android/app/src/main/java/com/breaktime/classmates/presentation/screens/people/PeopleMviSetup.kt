package com.breaktime.classmates.presentation.screens.people

import androidx.compose.runtime.MutableState
import com.breaktime.classmates.domain.entities.data.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

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