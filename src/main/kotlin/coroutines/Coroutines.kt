package coroutines

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * # Coroutines:
 * https://kotlinlang.org/docs/coroutines-overview.html
 *
 * 1) suspendable computations -> the idea that a function can suspend its execution at some point and resume later on
 * 2) coroutines are light-weight threads
 * 3) The function signature remains exactly the same. The only difference is suspend being added to it. The return type however is the type we want to be returned
 * 4) The code is still written as if we were writing synchronous code, top-down, without the need of any special syntax (except async to launch the job)
 * 5) The programming model and APIs remain the same. We can continue to use loops, exception handling, etc. and there's no need to learn a complete set of new APIs
 * 6) Go language has the same thing
 * 7) Most of the functionality is delegated to library
 *
 * Notes:
 *      -
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

suspend fun complexCall() = coroutineScope {
    println("Start: complexCall()")
    val deferredResult1 = async { call3() } // 3s
    val deferredResult2 = async { call4() } // 4s
    println("End: complexCall()")
    "ComplexResult(${deferredResult1.await()},${deferredResult2.await()})"
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
