import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

fun completableFeature() {

    val call1 = completableResult1()
    val call2 = failingCompletableFeature()
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

// Simulates normal asynchronous task
fun completableResult1(): CompletableFuture<String> {
    return CompletableFuture.completedFuture("Result 1")
}

// Simulates a task that will complete after some delay
fun completableResult2(): CompletableFuture<String> {
    return CompletableFuture.supplyAsync({
        "Result 2"
    }, CompletableFuture.delayedExecutor(2L, TimeUnit.SECONDS))
}

// Simulates a task that will complete with some exception
fun failingCompletableFeature(): CompletableFuture<String> {
    return CompletableFuture.failedFuture(RuntimeException("Failed task"))
}
