package coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * # Coroutines:
 *
 * 1) Kotlin de facto concurrency library
 * Made by team that created the language
 * Default solution for concurrent tasks on Android (so it is not going anywhere)
 * Implements something what is called structured concurrency
 * No operators to learn
 * No nesting/composing/chaining
 * Sequential code
 * During compile time the compiler will compile each suspend method into a state machine
 * No data race because you can be in only one state at a time
 * Catch exceptions using normal try catch
 * Easily propagate said exceptions
 * Supported by Spring Boot (Webflux) for http requests and as a controller response type
 * Full compatibility with: CompletableFuture - .await() extension method
 * Full compatibility with: Project reactor - .await() extensions (+ some helpful ones)
 * Simple api (~5 important keywords)
 * Cancelable at any time during execution
 * each suspend method has to be part of some other suspend method or a part of coroutine context
 * each job that we expect will take som time is run from inside of async { ... } method
 */

suspend fun coroutines() {
    return coroutineScope {

        val deferredResult1 = async { asyncTask1() }
        val deferredResult2 = async { asyncTask2() }

        val result1 = deferredResult1.await()
        val result2 = deferredResult2.await()

        println(result1)
        println(result2)
    }
}

suspend fun asyncTask1(): String {
    println("Start: asyncTask1()")
    delay(2000)
    println("End: asyncTask1()")
    return "Result 1 after 2s"
}

suspend fun asyncTask2(): String {
    println("Start: asyncTask2()")
    delay(5000)
    println("End: asyncTask2()")
    return "Result 2 after 5s"
}

suspend fun withException(): String {
    println("Start: withException()")
    delay(1000)
    throw RuntimeException("Exception message")
}
