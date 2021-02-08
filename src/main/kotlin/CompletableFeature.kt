import java.net.http.HttpClient
import java.util.concurrent.CompletableFuture

fun completableFeature() {
    val client: HttpClient = HttpClient.newBuilder().build()

    val call1 = apiCall1(client)
    val call2 = apiCall2(client)
    println("Created calls")

    val calls = listOf(call1, call2)
    val resultSequence = CompletableFuture.allOf(call1, call2).thenApply {
        calls.asSequence().map { call -> call.join() }
    }
    println("Awaiting calls")

    // Block and wait for requests to end
    resultSequence.join()

    println(call1.get())
    println(call2.get())

    println("Completed calls")
}
