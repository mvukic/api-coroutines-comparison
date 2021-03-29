package reactive

import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

/**
 *
 * # Project reactor / reactive extensions:
 * https://projectreactor.io/
 *
 * 1) introduced to C# (mainstream adoption after Netflix ported it to Java -> RxJava)
 * 2) Rx is simply the Observer Pattern with a series of extensions which allow us to operate on the data
 * 3) Future is returning a discrete element, whereby Rx returns a stream ( 1 or elements)
 *
 * 4) Usually there's a need to learn a completely new API such as map or flatMap
 * 5) The return type moves away from the actual data that we need and instead returns a new wrapper type which
 *
 * 6) Many calls to different microservices introduce latency
 * 7) Avoiding blocking calls which waste resources
 * 8) Better scalability because it supports high request load over a period of time
 * 9) Does not use request-per-thread model
 * 10) Uses Netty in Spring Boot - Event Loop Model (servlet 3.1+ specification)
 *      - https://dzone.com/articles/spring-webflux-eventloop-vs-thread-per-request-mod
 *
 * Notes:
 * - https://blog.softwaremill.com/will-project-loom-obliterate-java-futures-fb1a28508232
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
    println("Thread: ${Thread.currentThread().name}")
    return Mono.just("Result 1")
}

fun reactiveSource2(): Mono<String> {
    println("Thread: ${Thread.currentThread().name}")
    return Mono.just("Result 2")
}

fun failingReactiveSource(): Mono<String> {
    return Mono.error(RuntimeException("Failing result"))
}

fun emptyMono(): Mono<String> {
    return Mono.empty()
}
