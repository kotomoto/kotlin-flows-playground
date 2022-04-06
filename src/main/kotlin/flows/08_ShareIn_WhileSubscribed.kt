package flows

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    val flow = flowOf("A", "B", "C", "D")
        .onStart { println("Started") }
        .onCompletion { println("Finished") }
        .onEach { delay(1000) }
    val sharedFlow = flow.shareIn(
        scope = this,
        started = SharingStarted.WhileSubscribed(),
    )
    delay(3000)
    launch {
        println("#1 ${sharedFlow.take(2).toList()}")
    }
    launch {
        println("#2 ${sharedFlow.first()}")
    }
    delay(3000)
    launch {
        println("#3 ${sharedFlow.first()}")
    }
}