package flows

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    val mutableSharedFlow = MutableSharedFlow<String>(replay = 2)

    mutableSharedFlow.emit("Message 1")
    mutableSharedFlow.emit("Message 2")
    mutableSharedFlow.emit("Message 3")

    println(mutableSharedFlow.replayCache)

    launch {
        mutableSharedFlow.collect {
            println("#1 received $it")
        }
    }

    delay(100)
//    mutableSharedFlow.resetReplayCache()
    println(mutableSharedFlow.replayCache)

    launch {
        mutableSharedFlow.collect {
            println("#2 received $it")
        }
    }
}