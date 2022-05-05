package com.breaktime.classmates.presentation.screens.login

import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import com.breaktime.classmates.navigation.Screen
import com.breaktime.classmates.util.resetActivationState
import kotlinx.coroutines.flow.collect

fun initLoginObservable(
    scope: CoroutineScope,
    viewModel: LoginScreenViewModel,
    navController: NavHostController,
    showLoginDialog: MutableState<Boolean>,
    showDataConfirmationDialog: MutableState<Boolean>,
    showLoadingDialog: MutableState<Boolean>,
) {
    scope.launch {
        viewModel.uiState.collect {
            scope.ensureActive()
            when (it.state) {
                is LoginScreenContract.LoginScreenState.NoSuchAccount -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog)
                    )
                }
                is LoginScreenContract.LoginScreenState.Loading -> {
                    resetActivationState(
                        activate = listOf(showLoadingDialog),
                    )
                }
                is LoginScreenContract.LoginScreenState.Idle -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog, showDataConfirmationDialog),
                        activate = listOf(showLoginDialog)
                    )
                }
                is LoginScreenContract.LoginScreenState.Confirmed -> {
                    navController.popBackStack()
                    navController.navigate(Screen.Main.route)
                    viewModel.clearState()
                    scope.cancel()
                }
                is LoginScreenContract.LoginScreenState.UserDataConfirmation -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog, showLoginDialog),
                        activate = listOf(showDataConfirmationDialog)
                    )
                }
            }
        }
    }
}