package interaction

import coroutines.intro.anotherJob
import coroutines.intro.job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.runBlocking
import reactive.intro.anotherPublisher
import reactive.intro.exceptionalPublisher
import reactive.intro.publisher
import reactor.core.publisher.Mono
import kotlin.system.measureTimeMillis


/**
 * Interaction reactor <-> coroutines
 *
 * https://github.com/reactor/reactor-kotlin-extensions
 *
 *
 */
fun main() {
    measureTimeMillis {
        runBlocking {
            try {
                interactions()
            } catch (ex: RuntimeException) {
                println("Global exception handler: ${ex.message}")
            }
        }
    }.let {
        println("${it}ms")
    }
}

suspend fun interactions() {
//    reactiveToCoroutines()
    reactiveToCoroutinesWithException()
}

suspend fun reactiveToCoroutines() = coroutineScope {
    val reactiveResult1 = async { publisher().awaitSingle() } // 1s
    val reactiveResult2 = async { anotherPublisher().awaitSingle() } // 2s

    val result1 = reactiveResult1.await()
    val result2 = reactiveResult2.await()

    val result = "$result1,$result2"
    println(result)
}

suspend fun reactiveToCoroutinesWithException() = coroutineScope {
    val reactiveResult1 = async { exceptionalPublisher().awaitSingle() } // 1s
    val reactiveResult2 = async { anotherPublisher().awaitSingle() } // 2s

    val result1 = reactiveResult1.await()
    val result2 = reactiveResult2.await()

    val result = "$result1,$result2"
    println(result)
}

fun coroutinesToReactive(): Mono<String> {
    return mono {
        val result1 = async { job() }
        val result2 = async { anotherJob() }

        "${result1.await()}, ${result2.await()}"
    }
}
