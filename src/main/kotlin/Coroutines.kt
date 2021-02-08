import kotlinx.coroutines.future.await
import java.net.http.HttpClient

suspend fun coroutines() {
    val client: HttpClient = HttpClient.newBuilder().build()

    val url1Result = apiCall1(client).await() // 1 second
    val url2Result = apiCall2(client).await() // 10 seconds

    println(url1Result)
    println(url2Result)

}