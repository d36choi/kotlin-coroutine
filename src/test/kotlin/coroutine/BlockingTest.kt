package coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class BlockingTest {
    @Test
    fun `코루틴이 실행되지 않고 메서드가 끝나는 경우`() {
        val threadName = Thread.currentThread().name
        println("$threadName start")

        GlobalScope.launch {
            val x = doSomething()
            print("done something, $x")
        }

        println("$threadName end")

        // Test worker start
        //Test worker end
    }

    @Test
    fun `코루틴이 실행되게 하려면`() {
        val threadName = Thread.currentThread().name
        println("$threadName start")

        runBlocking {
            val x = doSomething()
            println("done something, $x")
        }

        println("$threadName end")
        // Test worker start
        // do something in a suspend method: 55
        // done something, 55
        // Test worker end
    }

    @Test
    fun `코루틴 빌더 async 활용`() = runBlocking {
        val threadName = Thread.currentThread().name
        println("$threadName start")

        val job1: Deferred<Int> = async {
            doSomething(10)
        }

        val job2 = async {
            doSomething(100)
        }


        val res1 = job1.await()
        val res2 = job2.await()

        println("$res1 $res2")
        println("$threadName end")

        // Test worker @coroutine#1 start
        //do something in a suspend method: 55
        //do something in a suspend method: 5050
        //55 5050
        //Test worker @coroutine#1 end
    }

    private suspend fun doSomething(range: Int = 10): Int {
        var total = 0
        for (i in 1..range) {
            delay(10)
            total += i
        }
        println("do something in a suspend method: $total")
        return total
    }
}