package utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun dotterJob(scope: CoroutineScope, dots: Int = 80) =
    scope.launch {
        (1..dots).onEach {
            delay(500)
            print(".")
        }
    }
