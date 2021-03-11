package reactive

import reactor.core.publisher.Mono

// Project reactor

// Notes:
// Implementation of industry standard reactive specification on JVM (JS, .NET, ...)
// Supported by Spring Boot (Webflux) for http requests and as a controller response type
// Fully asynchronous and non blocking, more efficient with resources (including threads)
// Getting rid of REQUEST == THREAD -> better efficiency
// good for systems that contact multiple endpoints/remote api-s because it does not introduce notable latency
// Similar to RXJS used in Angular frontend
// A lot of different operators that can be composed easily to process the data (pipeline)
// Data is pushed to the consumer (publishers and subscribers)
// Similarities with functional programming paradigm


// Neo4j database driver has reactive repositories - allows us fully reactive pipeline for PMT
// A lot of different operators
// Whole flow needs to be composed of completable futures to take the full advantage of it
// Code is not written sequential but using those operators (basically a oneliner)
// Used in email-sender
// Still some difficulties when having to run jobs in parallel
fun reactive() {

    // Combine tasks and map the result
    val result = Mono.zip(reactiveSource1(), reactiveSource2()).map {
        val result1 = it.t1
        val result2 = it.t2
        "$result1, $result2"
    }

    // Some additional processing
    val mappedResult = result.map { "$it -> mapped/processed" }.block()

    println(mappedResult)
}

fun reactiveSource1(): Mono<String> {
    return Mono.just("Reactive result 1")
}

fun reactiveSource2(): Mono<String> {
    return Mono.just("Delayed reactive result 2")
}

fun failingReactiveSource(): Mono<String> {
    return Mono.error(RuntimeException("Failing result"))
}
