package threads

import kotlin.concurrent.thread

/**
 * # Threads:
 *
 * 1) Most primitive parallelism
 * 2) Hard exception handling and propagating
 * 3) Hard to combine threads and make some dependency flow
 * 4) To get the data we need to eventually join them which again blocks some thread
 * 5) A bit expensive to create thread per every action
 * 6) A bit expensive code wise (a lot of code is required just to organize tasks)
 */
fun threads() {
    var r1 = ""
    var r2 = ""

    println("Creating threads\n")
    val thread1 = thread(start = true) {
        // Compose threads
        r1 = call()
        var r3 = ""
        val thread3 = thread(start = true) {
            r3 = call()
        }
        thread3.join()
    }
    val thread2 = thread(start = true) {
        // Long task
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
