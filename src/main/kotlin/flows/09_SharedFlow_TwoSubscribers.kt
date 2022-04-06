package flows

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import utils.dotterJob

suspend fun main(): Unit = coroutineScope {
    val flow = MutableSharedFlow<Int>()
//    val flow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)

//    val flow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.SUSPEND)
//    val flow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
//    val flow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

//    val flow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.SUSPEND)
//    val flow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.DROP_LATEST)
//    val flow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.DROP_OLDEST)

//    val flow = MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND)
//    val flow = MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_LATEST)
//    val flow = MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)

//    val flow = MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.SUSPEND)
//    val flow = MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
//    val flow = MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

//    val flow = MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.SUSPEND)
//    val flow = MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.DROP_LATEST)
//    val flow = MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.DROP_OLDEST)

//    val flow = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND)
//    val flow = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_LATEST)
//    val flow = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)

//    val flow = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.SUSPEND)
//    val flow = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
//    val flow = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

//    val flow = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.SUSPEND)
//    val flow = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.DROP_LATEST)
//    val flow = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val produceJob = launch {
        (1..10).onEach { produce(flow, it) }
    }

    val slowCollectJob = launch {
        delay(2500)
        println("\nSlow subscriber arrives")
        flow.collect {
            println("\n>>>>>>>> Slow Collected $it with cache: ${flow.replayCache}")
            delay(6000)
        }
    }

    val fastCollectJob = launch {
        flow.collect {
            println("\n>>>>>>>> Fast Collected $it with cache: ${flow.replayCache}")
        }
    }

    dotterJob(this)
}

private suspend fun produce(flow: MutableSharedFlow<Int>, number: Int) {
    delay(1000)
    println("\nTry to emit $number, current cache: ${flow.replayCache}")
    flow.emit(number)
}