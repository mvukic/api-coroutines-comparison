package completable

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

/**
 * # CompletableFuture / Future/ Promise:
 *
 * 1) when we make a call, we're promised that at some point it will return with an object called a Promise, which can then be operated on
 * 2) programming model moves away from a top-down imperative approach to a compositional model with chained calls
 * 3) Traditional program structures such as loops, exception handling, etc. usually are no longer valid in this model
 * 4) Usually there's a need to learn a completely new API such as thenCompose or thenAccept
 * 5) The return type moves away from the actual data that we need and instead returns a new wrapper type which
 * 6) Error handling can be complicated
 *
 */
fun completableFeature() {

    val call1 = completableCall1()
    val call2 = completableCall2()
    println("Created calls")

    // Join/await those calls
    val calls = listOf(call1, call2)
    val resultSequence = CompletableFuture.allOf(call1, call2).thenApply {
        // Which call does what (what is the type)?
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


fun conditionalCompletableFeature(): CompletableFuture<String> {
    // Create calls
    val call1 = completableCall1()
    val call2 = completableCall2()

    // Await calls
    val calls = listOf(call1, call2)
    val resultSequence = CompletableFuture.allOf(call1, call2).thenApply {
        calls.asSequence().map { call -> call.join() }
    }
    resultSequence.join()

    // Do something else depending on previous results
    // - could be nothing, could me another async call
    return if (condition(call1.get(), call2.get())) {
        CompletableFuture.completedFuture(call1.get())
    } else {
        CompletableFuture.completedFuture(call2.get())
    }

}

fun condition(value1: String, value2: String): Boolean {
    return true
}

/**
 * Simulates normal asynchronous task
 */
fun completableCall1(): CompletableFuture<String> {
    return CompletableFuture.completedFuture("Result 1")
}

/**
 * Simulates a task that will complete after some delay
 */
fun completableCall2(): CompletableFuture<String> {
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
