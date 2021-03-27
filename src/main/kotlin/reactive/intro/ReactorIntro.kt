package reactive.intro

import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.Duration


fun main() {
    reactor()
}

fun reactor() {
    getTheResult()
//    processTheData()
//    composition()
//    parallelComposition()
//    complexExample1()
//    complexExample2()
}

fun getTheResult() {
    val publisher: Mono<String> = publisher()

//    publisher.block() // DO NOT DO THIS
    publisher.subscribe {
        println(it)
    }
}

fun processTheData() {
    val publisher: Mono<String> = publisher()
    publisher.map {
        "Some processing: $it"
    }.subscribe {
        println(it)
    }
}

fun composition() {
    val publisher: Mono<String> = publisher()
    publisher.flatMap {
        // Make another call with previous result
        anotherPublisherWithInput(it)
    }.flatMap {
        // Make another call with previous result
        anotherPublisherWithInput(it)
    }.subscribe {
        println()
    }
}

fun parallelComposition() {
    val startTime = System.currentTimeMillis()
    Mono.zip(publisher(), anotherPublisher())
        .flatMap {
            Mono.just("${it.t1}, ${it.t2}")
        }.subscribe {
            println(it)
            println(System.currentTimeMillis() - startTime)
        }
    Thread.sleep(5000)
}

fun complexExample1() {
    Mono.zip(publisher(), anotherPublisher()).flatMap {
        if (it.t1.isNotEmpty() && it.t2.isNotEmpty()) {
            anotherPublisherWithInput("${it.t1},${it.t2}")
        } else {
            Mono.empty()
        }
    }
        .switchIfEmpty {
            Mono.just("")
        }.subscribe {
            println(it)
        }
}

fun complexExample2() {
    Mono.zip(publisher(), anotherPublisher()).flatMap {
        if (it.t1.isNotEmpty() && it.t2.isNotEmpty()) {
            anotherPublisherWithInput("${it.t1},${it.t2}")
        } else {
            Mono.error(RuntimeException("Values were empty!!!"))
        }

    }
        .onErrorContinue { throwable, _ ->
            Mono.just("Just disregard the error: ${throwable.message}")
        }.subscribe {
            println(it)
        }

}

fun publisher(): Mono<String> {
    return Mono.just("Some_data_1").delayElement(Duration.ofMillis(2000))
}

fun anotherPublisher(): Mono<String> {
    return Mono.just("Some_data_2").delayElement(Duration.ofMillis(1000))
}

fun anotherPublisherWithInput(input: String): Mono<String> {
    return Mono.just("Composed: $input")
}

fun exceptionalPublisher(): Mono<String> {
    return Mono.just("").delayElement(Duration.ofMillis(1000)).flatMap {
        Mono.error(RuntimeException("Exception"))
    }
}
