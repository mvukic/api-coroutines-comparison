import kotlinx.coroutines.runBlocking

fun main() {
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




