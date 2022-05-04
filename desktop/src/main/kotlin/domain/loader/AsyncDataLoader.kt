package domain.loader

import di.kodein
import domain.exceptions_broadscast.ConnectionExceptionsBroadcast
import domain.exceptions_broadscast.exceptions.NoConnectionException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.kodein.di.instance
import org.kodein.di.newInstance
import java.net.ConnectException

class AsyncDataLoader(private val exceptionsBroadcast: ConnectionExceptionsBroadcast) : CoroutineScope {
    override var coroutineContext = Dispatchers.IO + SupervisorJob()

    private val asyncChatLoader by kodein.newInstance { AsyncChatLoader(this@AsyncDataLoader, instance()) }
    private val asyncPeopleLoader by kodein.newInstance { AsyncPeopleLoader(this@AsyncDataLoader, instance()) }

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
                        if (it != currentList) {
                            updatedDateNotifier?.emit(Any())
                            currentList = it
                            flowDestination.emit(it)
                        }
                    }
                    exceptionsBroadcast.sendException(null)
                    delay(checkDelay)
                } catch (e: Exception) {
                    exceptionsBroadcast.sendException(e)
                    if (e is NoConnectionException || e is ConnectException) delay(internetErrorDelay)
                    else break
                }
            } while (true)
        }
    }

    class ValueWrapper<T>(var value: T)
}