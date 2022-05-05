package com.breaktime.classmates.presentation.screens.profile

import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController

import com.breaktime.classmates.util.resetActivationState
import com.breaktime.classmates.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun initProfileObservable(
    scope: CoroutineScope,
    viewModel: ProfileScreenViewModel,
    navController: NavHostController,
    showNoInternetConnectionDialog: MutableState<Boolean>,
    showLoadingDialog: MutableState<Boolean>,
) {
    scope.launch {
        viewModel.uiState.collect {
            scope.ensureActive()
            when (it.profileScreenState) {
                is ProfileScreenContract.ProfileScreenState.Saved -> {
                    resetActivationState(
                        disActivate = listOf(showNoInternetConnectionDialog, showLoadingDialog),
                    )
                }
                is ProfileScreenContract.ProfileScreenState.Idle -> {
                    resetActivationState(
                        disActivate = listOf(showNoInternetConnectionDialog, showLoadingDialog)
                    )
                }
                is ProfileScreenContract.ProfileScreenState.Loading -> {
                    resetActivationState(
                        activate = listOf(showLoadingDialog),
                        disActivate = listOf(showNoInternetConnectionDialog)
                    )
                }
                is ProfileScreenContract.ProfileScreenState.NoInternetConnection -> {
                    resetActivationState(
                        activate = listOf(showNoInternetConnectionDialog),
                        disActivate = listOf(showLoadingDialog)
                    )
                }
                is ProfileScreenContract.ProfileScreenState.Deleted,
                is ProfileScreenContract.ProfileScreenState.LoggedOut,
                -> {
                    resetActivationState(
                        disActivate = listOf(showLoadingDialog, showNoInternetConnectionDialog)
                    )
//                    navController.clearBackStack()
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
                    // TODO: 14.04.22 change MutableState for profile data
                }
            }
        }
    }
}