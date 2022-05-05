package com.breaktime.classmates.util

private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

fun String.isEmail() = EMAIL_REGEX.toRegex().matches(this)
