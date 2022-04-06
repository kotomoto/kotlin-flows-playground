package flows

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import utils.dotterJob

suspend fun main(): Unit = coroutineScope {
    dotterJob(this, 18)

    launch {
        val flow = makeFlow("Dumb")
        delay(3500)
        flow.collect { println("Dumb flow collected $it") }
    }

    launch {
        val flow = makeFlow("Shared").shareIn(this, SharingStarted.Lazily)
        delay(3500)
        flow.collect { println("Shared flow collected $it") }
    }
}

private fun makeFlow(type: String) = flow {
    println("$type flow started")
    for (i in 1..6) {
        delay(1000)
        emit(i)
    }
}
