package flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import utils.dotterJob

suspend fun main(): Unit = coroutineScope {
    dotterJob(this, 18)

    launch {
        val flow = makeSimpleFlow()
        delay(3500)
        flow.collect { println("Dumb flow collected $it") }
    }

    launch {
        val flow = makeSharedFlow(this)
        delay(3500)
        flow.collect { println("Shared flow collected $it") }
    }
}

private fun makeSimpleFlow() = flow {
    println("Dumb flow started")
    for (i in 1..6) {
        delay(1000)
        emit(i)
    }
}

private suspend fun makeSharedFlow(scope: CoroutineScope): MutableSharedFlow<Int> {
    println("Shared flow started")
    val flow = MutableSharedFlow<Int>()
    scope.launch {
        for (i in 1..6) {
            delay(1000)
            flow.emit(i)
        }
    }
    return flow
}

