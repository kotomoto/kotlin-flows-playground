package sharedflows

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main(): Unit = coroutineScope {
//    val flow = MutableSharedFlow<Int>(replay = 0, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND)
    val flow = MutableSharedFlow<Int>(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND)

    val produceJob = launch {
        (1..6).onEach { produce(flow, it) }
    }

    val slowCollectJob = launch {
        delay(1500)
        println("\nSlow subscriber arrives")
        flow.collect {
            println("\n>>>>>>>> Slow Collected $it with cache: ${flow.replayCache}")
            delay(6000)
        }
    }

    val fastCollectJob = launch {
        delay(2500)
        println("\nFast subscriber arrives")
        flow.collect {
            println("\n>>>>>>>> Fast Collected $it with cache: ${flow.replayCache}")
        }
    }

    val dotterJob = launch {
        (1..60).onEach {
            delay(500)
            print(".")
        }
    }
}

private suspend fun produce(flow: MutableSharedFlow<Int>, number: Int) {
    delay(1000)
    println("\nTry to emit $number")
    flow.emit(number)
}