package reactive

import reactor.core.publisher.Mono

/**
 *
 * # Project reactor:
 * 1) Implementation of industry standard reactive specification on JVM
 *      - JS, .NET, ...
 *      - similar to RXJS used in Angular frontend
 *      - publisher/subscriber - nothing happens until something happens
 *      - data is pushed to the consumers
 * 4) Supported by Spring Boot (Webflux)
 *      - default http client (Webflux)
 *      - controller return type support (Webflux)
 *      - JPA repositories do not support CompletableFuture (needs additional setup)
 * 5) Fully asynchronous and non blocking, more efficient with resources
 *      - getting rid of REQUEST == THREAD
 * 6) Good for systems that contact multiple endpoints/remote api-s
 *      -  it does not introduce notable latency
 * 7) Similarities with functional programming
 *      - immutability and mapping/filtering
 * 8) Operators to define the pipeline of data flow
 *      - for almost everything
 *      - .map(...), .switchMap(...), .zip(...), .delay(), .filter(...), .log(...)
 *      - need to remember which one does what
 * 9) Whole flow needs to use this api (no .block()) for best resource management
 * 10) Code is not written sequential but using those operators
 *      - one big oneliner
 *      - e.g. return call().operator1().operator2()...operatorN()
 * 11) Used in email-sender microservice
 * 12) Neo4j database library supports reactive api from repository level
 *      - allows us fully reactive non-blocking pipeline for PMT
 * 13) Still requires a bit of work to run tasks in parallel
 *      - .zip(task1, task2, ...) is limited because of predefined number of arguments
 *      - need to think which tasks should be run on which thread group (this is a bit simplified)
 */
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
