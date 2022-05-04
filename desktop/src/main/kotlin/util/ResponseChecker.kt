package util

import domain.exceptions_broadscast.exceptions.BadInputException
import domain.exceptions_broadscast.exceptions.ForbiddenException
import domain.exceptions_broadscast.exceptions.UnauthorisedException
import retrofit2.Response

inline fun Response<Any>.checkResponseCode() {
    when (this.code()) {
        400 -> throw BadInputException()
        401 -> throw UnauthorisedException()
        403 -> throw ForbiddenException()
    }
}