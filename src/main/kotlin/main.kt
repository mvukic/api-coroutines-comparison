import kotlinx.coroutines.runBlocking
import java.time.Duration
import kotlin.system.measureTimeMillis

fun main() {
    println("Start")
    println()
    val duration = measureTimeMillis {
        runBlocking {
            // try catch simulates GlobalExceptionHandler
            try {
                coroutines()
            } catch (e: Exception) {
                when (e) {
                    is Exception1 -> println("ApiCall 1 ${e.message}")
                    is Exception2 -> println("ApiCall 2 ${e.message}")
                }
            }
        }
    }
    println()
    println("End $duration")
//    try {
//        completableFeature()
//    } catch (e: Exception) {
//        when (e) {
//            is Exception1 -> println("ApiCall 1 ${e.message}")
//            is Exception2 -> println("ApiCall 2 ${e.message}")
//        }
//        println(e.message)
//    }
}




