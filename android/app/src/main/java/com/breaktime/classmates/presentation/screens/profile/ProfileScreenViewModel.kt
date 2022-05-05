package com.breaktime.classmates.presentation.screens.profile

import com.breaktime.classmates.domain.entities.data.CurrentUser
import com.breaktime.classmates.domain.use_cases.authorization.SignOutUseCase
import com.breaktime.classmates.domain.use_cases.people.GetProfileInfoUseCase
import com.breaktime.classmates.domain.use_cases.user_info.LoadProfileInfoUseCase
import com.breaktime.classmates.domain.use_cases.user_info.UpdateProfileInfoUseCase
import com.breaktime.classmates.presentation.base.BaseViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val updateProfileInfoUseCase: UpdateProfileInfoUseCase,
    private val loadProfileInfoUseCase: LoadProfileInfoUseCase,
) :
    BaseViewModel<ProfileScreenContract.Event, ProfileScreenContract.State, ProfileScreenContract.Effect>() {

    override fun createInitialState(): ProfileScreenContract.State {
        return ProfileScreenContract.State(
            ProfileScreenContract.ProfileScreenState.Idle
        )
    }

    override fun handleEvent(event: ProfileScreenContract.Event) {
        when (event) {
            is ProfileScreenContract.Event.OnLogOutBtnClick -> {
                logOut()
            }
            is ProfileScreenContract.Event.OnSaveInfo -> {
                launch {
                    updateProfileInfoUseCase(
                        event.name,
                        event.surname,
                        event.bio
                    )
                }.invokeOnCompletion {
                    launch { loadProfileInfoUseCase(CurrentUser.token) }
                }
            }
        }
    }

    private fun logOut() {
        setState { copy(state = ProfileScreenContract.ProfileScreenState.Loading) }
        MainScope().launch {
            signOutUseCase()
            setState { copy(state = ProfileScreenContract.ProfileScreenState.LoggedOut) }
        }
    }

    override fun clearState() {
        setState { copy(state = ProfileScreenContract.ProfileScreenState.Idle) }
    }
}