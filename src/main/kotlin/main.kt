import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
//    println("Start completable feature example")
// try catch simulates GlobalExceptionHandler
//    try {
//        completableFeature()
//    } catch (e: Exception) {
//        when (e) {
//            is Exception1 -> println("Call 1 ${e.message}")
//            is Exception2 -> println("Call 2 ${e.message}")
//            else -> println("Uncaught exception: ${e.message}")
//        }
//    }

//    println("Start reactive example")
// try catch simulates GlobalExceptionHandler
//    try {
//        reactive()
//    } catch (e: Exception) {
//        when (e) {
//            is Exception1 -> println("ApiCall 1 ${e.message}")
//            is Exception2 -> println("ApiCall 2 ${e.message}")
//            else -> println("Uncaught exception: ${e.message}")
//        }
//    }


    println("Start coroutines example")
//     Simulate main thread
    measureTimeMillis {
        runBlocking {
            // try catch simulates GlobalExceptionHandler
            try {
                coroutines()
            } catch (e: Exception) {
                // Simulate global exception handler
                when (e) {
                    is Exception1 -> println("Call 1 ${e.message}")
                    is Exception2 -> println("Call 2 ${e.message}")
                    else -> println("Uncaught exception: ${e.message}")
                }
            }
        }
    }.let {
        println("$it ms")
    }
}




