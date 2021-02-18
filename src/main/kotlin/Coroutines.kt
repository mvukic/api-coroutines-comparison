import kotlinx.coroutines.*

suspend fun coroutines() = coroutineScope {
//    val client: HttpClient = HttpClient.newBuilder().build()
//
//    val url2Result = apiCall2(client).await()
//    val url1Result = apiCall1(client).await()
//
//    println(url1Result)
//    println(url2Result)

    val result1 = async { delay2() }
    val result2 = async { delay5() }

    println(result1.await())
    println(result2.await())

}

suspend fun delay2(): String {
    println("Start: Call delay2()")
    delay(2000)
    println("End: Call delay2()")
    return "Delay of 2s"
}

suspend fun delay5(): String {
    println("Start: Call delay5()")
    delay(5000)
    println("End: Call delay5()")
    return "Delay of 5s"
}