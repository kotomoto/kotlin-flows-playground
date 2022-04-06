package flows

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun main(): Unit {
    val users = usersFlow()

    withContext(CoroutineName("Name")) {
        val job = launch {
            users.collect { println(it) }
        }

        launch {
            delay(2500)
            println("I got enough")
            job.cancel()
        }
    }
}

fun usersFlow(): Flow<String> = flow {
    repeat(3) {
        delay(1000)
        val ctx = currentCoroutineContext()
        val name = ctx[CoroutineName]?.name
        emit("User$it in $name")
    }
}