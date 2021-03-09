import reactor.core.publisher.Mono

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
    return Mono.just("Result 1")
}

fun reactiveSource2(): Mono<String> {
    return Mono.just("Result 2")
}

fun failingReactiveSource(): Mono<String> {
    return Mono.error(RuntimeException("Failing result"))
}
