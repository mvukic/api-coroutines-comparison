import completable.completableFeature
import coroutines.conditionalCoroutines
import coroutines.coroutines
import kotlinx.coroutines.runBlocking
import reactive.reactive
import threads.threads
import kotlin.system.measureTimeMillis

fun main() {
//    threadExample()
//    completableFutureExample()
//    reactorExample()
    coroutinesExample()
}

fun completableFutureExample() {
    println("Start completable feature example")
    try {
        completableFeature()
    } catch (e: Exception) {
        when (e) {
            is CustomException -> println("Exception: ${e.message}")
            else -> println("Uncaught exception: ${e.message}")
        }
    }
}

fun reactorExample() {
    println("Start reactive example")
    try {
        println(reactive().block())
    } catch (e: Exception) {
        when (e) {
            is CustomException -> println("Exception: ${e.message}")
            else -> println("Uncaught exception: ${e.message}")
        }
    }
}

fun coroutinesExample() {
    println("Start coroutines example")
    measureTimeMillis {
        runBlocking {
            try {
                coroutines()
//                conditionalCoroutines()
            } catch (e: Exception) {
                when (e) {
                    is CustomException -> println("Exception: ${e.message}")
                    else -> println("Uncaught exception: ${e.message}")
                }
            }
        }
    }.let {
        println("$it ms")
    }
}

fun threadExample() {
    println("Start thread example")
    measureTimeMillis {
        try {
            println(threads())
        } catch (e: Exception) {
            when (e) {
                is CustomException -> println("Exception: ${e.message}")
                else -> println("Uncaught exception: ${e.message}")
            }
        }
    }.let {
        println("$it ms")
    }
}




