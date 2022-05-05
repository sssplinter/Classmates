package com.breaktime.classmates.presentation.screens.profile

import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.breaktime.classmates.navigation.Screen
import com.breaktime.classmates.util.resetActivationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun initProfileObservable(
    scope: CoroutineScope,
    viewModel: ProfileScreenViewModel,
    navController: NavHostController,
    showLoadingDialog: MutableState<Boolean>,
    name: MutableState<String>,
    surname: MutableState<String>,
    bio: MutableState<String>,
) {
    scope.launch {
        viewModel.uiState.collect {
            scope.ensureActive()
            when (it.state) {
                is ProfileScreenContract.ProfileScreenState.Saved -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog),
                    )
                }
                is ProfileScreenContract.ProfileScreenState.Idle -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog)
                    )
                }
                is ProfileScreenContract.ProfileScreenState.Loading -> {
                    resetActivationState(
                        activate = listOf(showLoadingDialog),
                    )
                }
                is ProfileScreenContract.ProfileScreenState.LoggedOut -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog)
                    )
                    navController.backQueue.clear()
                    navController.navigate(Screen.Login.route)
                    viewModel.clearState()
                    scope.cancel()
                }
            }
        }
    }
    scope.launch {
        viewModel.effect.collect {
            scope.ensureActive()
            when (it) {
                is ProfileScreenContract.Effect.DataLoaded -> {
                    name.value = it.name
                    surname.value = it.surname
                    bio.value = it.bio
                }
            }
        }
    }
}