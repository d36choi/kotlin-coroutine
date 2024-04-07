package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DispatcherTest {

    @Test
    fun testDefaultDispatcher() = runBlocking {
        println("Main thread: ${Thread.currentThread().name}")

        // Dispatchers.Default를 사용하여 작업 실행
        val result = withContext(Dispatchers.Default) {
            println("Coroutine thread: ${Thread.currentThread().name}")
            delay(1000)
            42
        }

        assertEquals(result, 42)
    }

    @Test
    fun testIODispatcher() = runBlocking {
        println("Main thread: ${Thread.currentThread().name}")

        // Dispatchers.IO를 사용하여 작업 실행
        val result = withContext(Dispatchers.IO) {
            println("Coroutine thread: ${Thread.currentThread().name}")
            delay(1000)
            42
        }

        assertEquals(result, 42)
    }
}