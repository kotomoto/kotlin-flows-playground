package flows

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import utils.dotterJob

suspend fun main(): Unit = coroutineScope {
    val flow = MutableSharedFlow<Int>()
//    val flow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND)

//    val flow = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND)
//    val flow = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)

//    val flow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.SUSPEND)

//    val flow = MutableSharedFlow<Int>(replay = 10, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND)
//    val flow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 10, onBufferOverflow = BufferOverflow.SUSPEND)

//    val flow = MutableSharedFlow<Int>(replay = 2, extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.SUSPEND)

    val dotterJob = dotterJob(this)

    val produceJob = launch {
        (1..10).forEach { produce(flow, it) }
    }

    val collectJob = launch {
        delay(2500)
        println("Subscriber arrives")
        flow.collect {
            println("Collected $it")
            if (it == 10) dotterJob.cancel()
            delay(4000)
        }
    }
}

private suspend fun produce(flow: MutableSharedFlow<Int>, number: Int) {
    delay(1000)
    println("\nTry to emit $number")
    flow.emit(number)
}