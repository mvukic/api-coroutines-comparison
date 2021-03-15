package completable

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

/**
 * # CompletableFuture:
 *
 * 1) Introduced in Java 8 (and improved upon in Java 9)
 * 2) Better then threads:
 *      - regarding code in general
 *      - regarding composition of tasks
 *      - regarding better resource handling (memory leaks, ...)
 *      - regarding code readability
 * 3) Easier grouping tasks per thread
 * 4) Supported by Spring Boot (MVC and Webflux)
 *      - default http client (MVC)
 *      - controller return type support (MVC, Webflux)
 * 5) Easier catching and propagating of exceptions
 * 6) Still a bit complex to compose tasks
 * 7) Whole flow needs to use this api (no .get() or .block()) for best resource management
 * 8) Once CompletableFuture has started it cannot be canceled
 * 9) Consuming of result with .thenApply()/.thenApplyAsync()
 *      - can be chained task.thenApplyAsync().thenApplyAsync()
 *      - mapping CompletableFuture to some other value
 * 10) Composing of result with .thenCompose()/.thenComposeAsync()
 *      - can be chained task.thenComposeAsync().thenComposeAsync()
 *      - mapping of CompletableFuture to other CompletableFuture
 * 11) Api does not offer many features
 * 12) Used in NMT for ELIS api calls (all api calls)
 */
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
