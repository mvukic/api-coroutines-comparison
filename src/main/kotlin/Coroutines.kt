import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

suspend fun coroutines() {
    return coroutineScope {

//        val deferredResult1 = asyncTask1()
//        val deferredResult2 = asyncTask2()
//
//        println(deferredResult1)
//        println(deferredResult2)

        val deferredResult1 = async { asyncTask1() }
        val deferredResult2 = async { asyncTask2() }

        val result1 = deferredResult1.await()
        val result2 = deferredResult2.await()

        println(result1)
        println(result2)
    }
}

suspend fun asyncTask1(): String {
    println("Start: asyncTask1()")
    delay(2000)
    println("End: asyncTask1()")
    return "Result 1 after 2s"
}

suspend fun asyncTask2(): String {
    println("Start: asyncTask2()")
    delay(5000)
    println("End: asyncTask2()")
    return "Result 2 after 5s"
}

suspend fun withException(): String {
    println("Start: withException()")
    delay(1000)
    throw RuntimeException("Exception message")
    println("End: withException()")
    return "Result 2 after 5s"
}
