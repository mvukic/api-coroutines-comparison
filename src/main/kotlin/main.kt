import threads.threads
import kotlin.system.measureTimeMillis

fun main() {
//    println("Start completable feature example")
//    try {
//        completableFeature()
//    } catch (e: Exception) {
//        when (e) {
//            is CustomException -> println("Exception: ${e.message}")
//            else -> println("Uncaught exception: ${e.message}")
//        }
//    }
//
//    println("Start reactive example")
//    try {
//        reactive()
//    } catch (e: Exception) {
//        when (e) {
//            is CustomException -> println("Exception: ${e.message}")
//            else -> println("Uncaught exception: ${e.message}")
//        }
//    }
//
//
//    println("Start coroutines example")
//    measureTimeMillis {
//        runBlocking {
//            try {
//                coroutines()
//            } catch (e: Exception) {
//                when (e) {
//                    is CustomException -> println("Exception: ${e.message}")
//                    else -> println("Uncaught exception: ${e.message}")
//                }
//            }
//        }
//    }.let {
//        println("$it ms")
//    }

    println("Start thread example")
    measureTimeMillis {
        try {
            threads()
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




