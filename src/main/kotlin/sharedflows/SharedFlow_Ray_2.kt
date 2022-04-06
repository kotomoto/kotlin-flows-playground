package sharedflows

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main(): Unit = coroutineScope {
//    val flow = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND)
//    val flow = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.SUSPEND)
    val flow = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.SUSPEND)
//    val flow = MutableSharedFlow<String>(replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    launch {
        delay(1000)
        flow.tryEmit("A")
        delay(1000)
        flow.tryEmit("B")
        delay(1000)
        flow.tryEmit("C")
    }

    launch {
        delay(500)
        flow.collect {
            suspendCoroutine<Unit> { continuation ->
                thread {
                    println("Suspended")
                    Thread.sleep(6000)
                    continuation.resume(Unit)
                    println("Resumed")
                }
            }
            println("#1 collects $it")
        }
    }
    launch {
        delay(1500)
        flow.collect {
            println("#2 collects $it")
        }
    }
}

// replay: 0, extraBufferCapacity: 0
//#1 collects B
//#2 collects C
//#1 collects C

// replay: 1, extraBufferCapacity: 0
//#1 collects A
//#1 collects B
//#2 collects B
//#1 collects C
//#2 collects C

// replay: 0, extraBufferCapacity: 1
//#1 collects B
//#1 collects C
//#2 collects C

// replay: 1, extraBufferCapacity: 1
//#1 collects B
//#2 collects C
//#1 collects C