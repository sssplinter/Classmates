package com.breaktime.classmates.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.toTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("HH:mm")
    return format.format(date)
}