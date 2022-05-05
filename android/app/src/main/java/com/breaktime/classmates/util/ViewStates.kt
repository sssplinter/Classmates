package com.breaktime.classmates.util

import androidx.compose.runtime.MutableState

fun resetActivationState(
    activate: List<MutableState<Boolean>>? = null,
    disActivate: List<MutableState<Boolean>>? = null,
) {
    activate?.forEach { it.value = true }
    disActivate?.forEach { it.value = false }
}