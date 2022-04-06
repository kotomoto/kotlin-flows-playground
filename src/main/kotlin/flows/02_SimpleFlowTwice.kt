package flows

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import utils.dotterJob

suspend fun main(): Unit = coroutineScope {
    val flow = makeFlow()

    dotterJob(this, 18)

    delay(3000)

    println("Calling flow...")
    launch {
        delay(2000)
        flow.collect { value -> println("#1 Collected $value") } }

    println("Consuming again...")
    launch {flow.collect { value -> println("#2 Collected $value") } }
}

private fun makeFlow() = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(1000)
        emit(i)
    }
}