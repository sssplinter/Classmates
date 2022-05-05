package com.breaktime.classmates.util

import com.breaktime.classmates.domain.exceptions_broadscast.exceptions.BadInputException
import com.breaktime.classmates.domain.exceptions_broadscast.exceptions.ForbiddenException
import com.breaktime.classmates.domain.exceptions_broadscast.exceptions.UnauthorisedException
import retrofit2.Response

inline fun Response<Any>.checkResponseCode() {
    when (this.code()) {
        400 -> throw BadInputException()
        401 -> throw UnauthorisedException()
        403 -> throw ForbiddenException()
    }
}