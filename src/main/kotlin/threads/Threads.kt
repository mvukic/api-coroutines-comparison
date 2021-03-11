package threads

import kotlin.concurrent.thread

// Threads:

// Notes:
// Most primitive parallelism
// Hard exception handling and propagating
// Hard to combine threads and make some dependency flow
// Manual thinking of what needs to be run in thread
// To get the data we need to eventually join them which again block some thread
// Usually not used in user code but internally (because of the above reasons)
// A bit expensive
fun threads() {
    var r1 = ""
    var r2 = ""

    println("Creating threads\n")
    val thread1 = thread(start = true) {
        r1 = call()
    }
    val thread2 = thread(start = true) {
        r2 = blockingCall()
    }

    println("Await threads\n")
    listOf(thread1, thread2).forEach { it.join() }

    println("Get results")
    println(r1)
    println(r2)
}

fun call(): String {
    println("Start: call() ${Thread.currentThread().name}")
    Thread.sleep(1000)
    println("End: call() ${Thread.currentThread().name}")
    return "Call result"
}

fun blockingCall(): String {
    println("Start: blockingCall() ${Thread.currentThread().name}")
    Thread.sleep(3000)
    println("End: blockingCall() ${Thread.currentThread().name}")
    return "Blocking call result"
}
