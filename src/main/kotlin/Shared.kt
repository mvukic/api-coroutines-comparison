import kotlinx.coroutines.delay
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

fun createRequest1(): HttpRequest = HttpRequest.newBuilder()
    .uri(URI.create("https://jsonplaceholder.typicode.com/todos/1"))
    .build()

fun createRequest2(): HttpRequest = HttpRequest.newBuilder()
    .uri(URI.create("https://jsonplaceholder.typicode.com/users/1"))
    .build()

fun apiCall1(client: HttpClient): CompletableFuture<ResultData1> {
    println("Call 1 started")
    return client.sendAsync(createRequest1(), HttpResponse.BodyHandlers.ofString()).thenApply {
        when (it.statusCode()) {
            200 -> ResultData1(it.body())
            else -> throw Exception1("Request 1 failed")
        }
    }
}

fun apiCall2(client: HttpClient): CompletableFuture<ResultData2> {
    println("Call 2 started")
    return client.sendAsync(createRequest2(), HttpResponse.BodyHandlers.ofString()).thenApply {
        when (it.statusCode()) {
            200 -> ResultData2(it.body())
            else -> throw Exception2("Request 2 failed")
        }
    }
}

data class ResultData1(val data1: String)
data class ResultData2(val data2: String)

class Exception1(
    override val message: String
) : RuntimeException(message)

class Exception2(
    override val message: String
) : RuntimeException(message)