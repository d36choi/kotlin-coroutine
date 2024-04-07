import kotlinx.coroutines.*

fun main(args: Array<String>) {
    println("${Thread.currentThread().name} start")


    GlobalScope.launch {
        val x = doSomething()
        print("done something, $x")
    }

    println("main end")
}


private suspend fun doSomething():Int {
    val value: Int = GlobalScope.async(Dispatchers.IO) {
        var total = 0
        for (i in 1..10) {
            Thread.sleep(100)
            total += i
        }
        print("do something in a suspend method: $total")
        total
    }.await()
    return value
}