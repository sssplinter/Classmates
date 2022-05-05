package com.breaktime.classmates.domain.loader

import com.breaktime.classmates.domain.exceptions_broadscast.ConnectionExceptionsBroadcast
import com.breaktime.classmates.domain.exceptions_broadscast.exceptions.NoConnectionException
import com.breaktime.classmates.domain.source.chat.ChatRepository
import com.breaktime.classmates.domain.source.people.PeopleRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.ConnectException

class AsyncDataLoader(
    private val exceptionsBroadcast: ConnectionExceptionsBroadcast,
    chatRepository: ChatRepository,
    peopleRepository: PeopleRepository,
) : CoroutineScope {
    override var coroutineContext = Dispatchers.IO + SupervisorJob()

    val asyncChatLoader = AsyncChatLoader(this, chatRepository)
    val asyncPeopleLoader = AsyncPeopleLoader(this, peopleRepository)

    init {
        launchLoading()
    }

    private fun launchLoading() {
        asyncChatLoader.launchLoading()
        asyncPeopleLoader.launchLoading()
    }

    fun <T> dataLoading(
        isStartedLoading: ValueWrapper<Boolean>,
        dataProvider: suspend () -> List<T>,
        flowDestination: MutableSharedFlow<List<T>>,
        updatedDateNotifier: MutableStateFlow<Any?>? = null,
        checkDelay: Long,
        internetErrorDelay: Long,
    ) = launch {
        if (!isStartedLoading.value) {
            isStartedLoading.value = true
            var currentList: List<T>? = null
            do {
                try {
                    dataProvider().let {
                        flowDestination.emit(it)
                        if (it != currentList) {
                            updatedDateNotifier?.emit(Any())
                            currentList = it
                        }
                    }
                    exceptionsBroadcast.sendException(null)
                    delay(checkDelay)
                } catch (e: Exception) {
                    exceptionsBroadcast.sendException(e)
                    when (e) {
                        is NoConnectionException,
                        is ConnectException,
                        -> delay(internetErrorDelay)
                        is NumberFormatException -> continue
                        else -> break
                    }
                }
            } while (true)
        }
    }

    class ValueWrapper<T>(var value: T)
}