package coroutines.intro

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        runBlocking {
            try {
                coroutines()
            } catch (ex: RuntimeException) {
                println(ex.message)
            }
        }
    }.let {
        println("${it}ms")
    }
}

suspend fun coroutines() {
    getTheResult()
//    processTheData()
//    composition()
//    parallelComposition()
//    complexExample1()
//    complexExample2()
//    exceptionWithLocalCatch()
//    exceptionWithGlobalCatch()
}





















suspend fun getTheResult() {
    val result = job() // 1s
    println(result)
}




















suspend fun processTheData() {
    val result = job() // 1s
    val processedData = "Some processing: $result"
    println(processedData)
}






















suspend fun composition() {
    val result1 = job() // 1s
    val result2 = anotherJobWithInput(result1) // 0s
    val result3 = anotherJobWithInput(result2) // 0s
    println(result3)
}



















suspend fun parallelComposition() = coroutineScope {
//    Deferred — a light-weight non-blocking future that represents a promise to provide a result later
    val deferredResult1: Deferred<String> = async { job() } // 1s
    val deferredResult2 = async { anotherJob() } // 2s

    val result1 = deferredResult1.await()
    val result2 = deferredResult2.await()

    val result = "${result1},${result2}"
    println(result)
}



















suspend fun complexExample1() = coroutineScope {
    val deferredResult1 = async { job() } // 1s
    val deferredResult2 = async { anotherJob() } // 2s

    val result1 = deferredResult1.await()
    val result2 = deferredResult2.await()

    val result = if (result1.isNotEmpty() && result2.isNotEmpty()) "$result1,$result2" else ""
    println(result)
}














suspend fun complexExample2() = coroutineScope {
    val deferredResult1 = async { job() } // 1s
    val deferredResult2 = async { anotherJob() } // 2s

    val result1 = deferredResult1.await()
    val result2 = deferredResult2.await()

    if (result1.isEmpty() || result2.isEmpty()) {
        throw RuntimeException("Values were empty!!!")
    }
    val result = "$result1,$result2"
    println(result)
}

















suspend fun exceptionWithLocalCatch() = coroutineScope {
    val deferredResult1 = async {
        try {
            exceptionalJob()
        } catch (ex: RuntimeException) {
            "this instead of exception"
        }
    } // 1s
    val deferredResult2 = async { anotherJob() } // 2s

    val result1 = deferredResult1.await()
    val result2 = deferredResult2.await()

    val result = "$result1,$result2"
    println(result)
}



















suspend fun exceptionWithGlobalCatch() = coroutineScope {
    val deferredResult1 = async { exceptionalJob() } // 1s
    val deferredResult2 = async { anotherJob() } // 2s

    val result1 = deferredResult1.await()
    val result2 = deferredResult2.await()

    val result = "$result1,$result2"
    println(result)
}

























suspend fun job(): String {
    delay(1000)
    return "Some_data_1"
}

suspend fun anotherJob(): String {
    delay(2000)
    return "Some_data_2"
}

fun anotherJobWithInput(input: String): String {
    return "Composed: $input"
}

suspend fun exceptionalJob(): String {
    delay(1000)
    throw RuntimeException("Exception")
}
