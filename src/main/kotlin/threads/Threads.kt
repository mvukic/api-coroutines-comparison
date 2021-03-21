package threads

import kotlin.concurrent.thread

/**
 * # Threads:
 *
 * 1) Threads aren't cheap. Threads require context switches which are costly.
 * 2) Threads aren't infinite
 * 3) The number of threads that can be launched is limited by the underlying operating system, In server-side applications, this could cause a major bottleneck.
 * 4) Threads aren't easy. Debugging threads, avoiding race conditions are common problems we suffer in multi-threaded programming.
 */
fun threads(): String {
    var r1 = ""
    var r2 = ""

    println("Creating threads\n")
    val thread1 = thread(start = true) {
        // Compose threads
        r1 = call1()
    }
    val thread2 = thread(start = true) {
        // Long task
        r2 = call2()
    }

    println("Await threads\n")
    listOf(thread1, thread2).forEach { it.join() }

    println("Get results")
    return "$r1 -> $r2"
}

fun conditionalThreads(): String {
    var r1 = ""
    var r2 = ""

    println("Creating threads\n")
    val thread1 = thread(start = true) {
        // Compose threads
        r1 = call1()
    }
    val thread2 = thread(start = true) {
        // Long task
        r2 = call2()
    }

    println("Await threads\n")
    listOf(thread1, thread2).forEach { it.join() }

    var r3 = ""
    return if (condition(r1, r2)) {
        val thread3 = thread(start = true) {
            // Long task
            r3 = call2()
        }
        thread3.join()
        r3
    } else {
        "$r1 -> $r2"
    }
}

fun condition(value1: String, value2: String): Boolean {
    return true
}

fun call1(): String {
    println("Start: call1() ${Thread.currentThread().name}")
    Thread.sleep(1000)
    println("End: call1() ${Thread.currentThread().name}")
    return "Result 1"
}

fun call2(): String {
    println("Start: call2() ${Thread.currentThread().name}")
    Thread.sleep(3000)
    println("End: call2() ${Thread.currentThread().name}")
    return "Result 2"
}
