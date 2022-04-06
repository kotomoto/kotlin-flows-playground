package flows

import kotlinx.coroutines.coroutineScope

suspend fun main(): Unit {
    val list = getSequence()
    list.forEach { println(it) }
}

fun getSequence(): Sequence<String> = sequence {
    repeat(3) {
        Thread.sleep(1000)
        yield("User$it")
    }
}