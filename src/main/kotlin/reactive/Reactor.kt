package reactive

import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

/**
 *
 * # Project reactor / reactive extensions:
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
 *
 * 1) introduced to C# (mainstream adoption after Netflix ported it to Java -> RxJava)
 * 2) Rx is simply the Observer Pattern with a series of extensions which allow us to operate on the data
 * 3) Future as returning a discrete element, whereby Rx returns a stream
 * 4) Usually there's a need to learn a completely new API such as map or flatMap
 * 5) The return type moves away from the actual data that we need and instead returns a new wrapper type which
 */
fun reactive(): Mono<String> {

    // Combine tasks (await them in parallel)
    return Mono.zip(reactiveSource1(), reactiveSource2())
        // Make them execute on different threads
        .publishOn(Schedulers.boundedElastic())
        // Map async result  to another async result
        .map {
            "${it.t1} -> ${it.t2}"
        }

}


fun conditionalReactive(): Mono<String> {
    return Mono.zip(reactiveSource1(), reactiveSource2())
        // Make them execute on different threads
        .publishOn(Schedulers.boundedElastic())
        // Check if condition is fulfilled
        .flatMap {
            // If ok then
            if (condition(it.t1, it.t2)) {
                reactiveSource1()
            } else {
                Mono.just("some other result")
            }
        }
}

fun condition(value1: String, value2: String): Boolean {
    return true
}

fun reactiveSource1(): Mono<String> {
    return Mono.just("Result 1")
}

fun reactiveSource2(): Mono<String> {
    return Mono.just("Result 2")
}


/**
 * Usage of Mono.error
 */
fun failingReactiveSource(): Mono<String> {
    return Mono.error(RuntimeException("Failing result"))
}

/**
 * Absence of a value using Mono.empty
 */
fun emptyMono(): Mono<String> {
    return Mono.empty()
}
