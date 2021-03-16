package coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * # Coroutines:
 *
 * 1) Kotlin de facto concurrency library
 * 2) Made by the same people that created Kotlin
 * 3) Default solution for concurrent tasks on Android
 *      - it is not going anywhere
 * 4) Represents structured concurrency
 *      - adds clarity, quality to concurrent programming
 *      - concurrency
 *          - two or more tasks can start, run and complete in overlapping time (not necessarily the same time)
 *          - multitasking on single core machines
 *      - parallelism
 *          - tasks are run at the same time
 *          - multi core processors
 *  5) CompletableFuture/Webflux differences
 *      - no operators
 *      - no chained one liners
 *      - sequential code
 *  6) Kotlin compiler
 *      - each coroutine is compiled to a state in a larger finite state machine
 *  7) Exceptions
 *      - using regular try/catch or whatever
 *      - easy propagation of those exceptions
 *  8) Supported by Spring Boot (MVC and Webflux)
 *      - controller return type support (MVC, Webflux)
 *  9) Easily used with existing solutions
 *      - because it is basically a library
 *      - adapters for CompletableFuture
 *          - .await() extension
 *          - builders to transform coroutines to CompletableFuture
 *      - adapters for Reactor
 *          - .await(), .awaitFirst(), .awaitFirstOrDefault() extension
 *          - builders for transforming coroutines to Reactor types
 *  10) Api is simple
 *      -  ~5 keywords
 *  11) Can be canceled
 *  12) Coroutines are used from within coroutine context
 *      - functions are prefixed with suspend keyword
 *      - tasks are run from within async { ... } block
 */

suspend fun coroutines() = coroutineScope {

    val deferredResult1 = async { asyncTask1() }
    val deferredResult2 = async { asyncTask2() }

    val result1 = deferredResult1.await()
    val result2 = deferredResult2.await()

    println(result1)
    println(result2)
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
