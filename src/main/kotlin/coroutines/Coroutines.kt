package coroutines

import kotlinx.coroutines.Deferred
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
 *      - functions that create coroutines are prefixed with suspend keyword
 *      - jobs can are run from within async { ... } block
 */

/**
 * 1) suspendable computations -> the idea that a function can suspend its execution at some point and resume later on
 * 2) coroutines are light-weight threads
 * 3) The function signature remains exactly the same. The only difference is suspend being added to it. The return type however is the type we want to be returned
 * 4) The code is still written as if we were writing synchronous code, top-down, without the need of any special syntax (except async to launch the job)
 * 5) The programming model and APIs remain the same. We can continue to use loops, exception handling, etc. and there's no need to learn a complete set of new APIs
 * 6) Go language has the same thing
 * 7) Most of the functionality is delegated to library
 */

suspend fun coroutines() = coroutineScope {

    // Create calls
    // Deferred — a light-weight non-blocking future that represents a promise to provide a result later
    val deferredResult1: Deferred<String> = async { call1() } // 1s
    val deferredResult2: Deferred<String> = async { call2() } // 2s
    val deferredResult3: Deferred<String> = async { complexCall() } // 3s + 4s = 7s

    // Get values
    println("Result 1: ${deferredResult1.await()}")
    println("Result 2: ${deferredResult2.await()}")
    println("Result 3: ${deferredResult3.await()}")
}

suspend fun complexCall(): String {
    println("Start: complexCall()")
    val deferredResult1 = call3() // 3s
    val deferredResult2 = call4() // 4s
    println("End: complexCall()")
    return "ComplexResult($deferredResult1,$deferredResult2)"
}

suspend fun conditionalCoroutines() = coroutineScope {
    // Create calls
    val deferredResult1 = async { call1() }
    val deferredResult2 = async { call2() }

    // Await results
    val result1 = deferredResult1.await()
    val result2 = deferredResult2.await()

    println("$result1 -> $result2")

    if (condition(result1, result2)) {
        println("Condition was true")
        val deferredResult3 = async { call3() }
        val deferredResult4 = async { call4() }
        println("${deferredResult3.await()} -> ${deferredResult4.await()}")
    }

}

fun condition(value1: String, value2: String): Boolean {
    return true
}


suspend fun call1(): String {
    println("Start: call1()")
    delay(1000)
    println("End: call1()")
    return "Result 1"
}

suspend fun call2(): String {
    println("Start: call2()")
    delay(2000)
    println("End: call2()")
    return "Result 2"
}

suspend fun call3(): String {
    println("Start: call3()")
    delay(3000)
    println("End: call3()")
    return "Result 3"
}

suspend fun call4(): String {
    println("Start: call4()")
    delay(4000)
    println("End: call4()")
    return "Result 4"
}

suspend fun callWithException(): String {
    println("Start: withException()")
    delay(2000)
    throw RuntimeException("Exception message")
}
