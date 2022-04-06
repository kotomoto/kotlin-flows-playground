package flows

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    val flow = flowOf("A", "B", "C").onEach { delay(1000) }

    val sharedFlow: SharedFlow<String> = flow
//        .onStart { println("Flow started") }
        .shareIn(
        scope = this,
        started = SharingStarted.Eagerly,
//        started = SharingStarted.Lazily,
//        replay = 2
    )

    delay(500)
    launch {
        println("First subscriber arrives")
        sharedFlow.collect { println("#1 collected $it") }
    }

    delay(800)
    launch {
        println("Second subscriber arrives")
        sharedFlow.collect { println("#2 collected $it") }
    }

    delay(1000)
    launch {
        println("Third subscriber arrives")
        sharedFlow.collect { println("#3 collected $it") }
    }
}