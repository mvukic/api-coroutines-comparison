package completable

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

// CompletableFuture

// Notes:
// Introduced in Java 8 (and improved upon in Java 9)
// Better then threads from user perspective
// Supported by Spring Boot (MVC and Webflux) for http requests and as a controller response type
// Automatically handles thread creation
// Getting result/catching exceptions

// Work needs to be put in for parallel execution
// Whole flow needs to be composed of completable futures to take the full advantage of it
// Once started it cannot be canceled
// Result precessing with .thenApplyAsync(...) which can be chained
// Compose using .thenComposeAsync(...) which can be chained
// Used in NMT
// A bit too much code has to be written just to do something simple as parallel execution
fun completableFeature() {

    val call1 = completableCall()
    val call2 = failingCompletableCall()
    println("Created calls")

    // Cumbersome to do complex parallel jobs
    val calls = listOf(call1, call2)
    val resultSequence = CompletableFuture.allOf(call1, call2).thenApply {
        // Which call does what?
        calls.asSequence().map { call -> call.join() }
    }
    println("Awaiting calls")

    // Block current thread and wait for requests to end
    resultSequence.join()

    // Get individual results
    println(call1.get())
    println(call2.get())

    println("Completed calls")
}

/**
 * Simulates normal asynchronous task
 */
fun completableCall(): CompletableFuture<String> {
    return CompletableFuture.completedFuture("Result 1")
}

/**
 * Simulates a task that will complete after some delay
 */
fun delayedCompletableCall(): CompletableFuture<String> {
    return CompletableFuture.supplyAsync({
        "Result 2"
    }, CompletableFuture.delayedExecutor(2L, TimeUnit.SECONDS))
}

/**
 * Simulates a task that will complete with some exception
 */
fun failingCompletableCall(): CompletableFuture<String> {
    return CompletableFuture.failedFuture(RuntimeException("Failed task"))
}
