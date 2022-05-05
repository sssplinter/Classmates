package com.breaktime.classmates.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

inline val Dp.sp: TextUnit
    @Composable
    get() = with(LocalDensity.current) { this@sp.toSp() }

inline val TextUnit.dp: Dp
    @Composable
    get() = with(LocalDensity.current) { this@dp.toDp() }
