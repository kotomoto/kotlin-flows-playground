package flows

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import utils.dotterJob

suspend fun main(): Unit = coroutineScope {
    dotterJob(this, 11)

    val flow = makeFlow()

    delay(3000)

    println("Calling flow...")
    flow.collect { value -> println("Collected $value") }
}

private fun makeFlow() = flow {
    println("Flow started")
    for (i in 1..3) {
        delay(1000)
        emit(i)
    }
}
