package coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoroutineBuilderTest {

    private suspend fun fakeApiCall(): Int {
        delay(1000) // 가짜 API 호출을 대신하기 위해 1초를 기다립니다.
        return 42
    }

    @Test
    fun testLaunch() = runBlocking {
        println("Main thread: ${Thread.currentThread().name}")

        // launch 빌더를 사용하여 새로운 코루틴 시작
        val job = GlobalScope.launch {
            println("Coroutine thread: ${Thread.currentThread().name}")
            val result = fakeApiCall()
            println("Coroutine executed with result: $result")
            assertEquals(result, 42)
        }
        // default dispatcher에서 코루틴 실행

        job.join() // 코루틴이 완료될 때까지 기다림
    }

    @Test
    fun testAsync() = runBlocking {
        println("Main thread: ${Thread.currentThread().name}")

        // async 빌더를 사용하여 비동기 작업 수행
        val deferred = GlobalScope.async {
            println("Coroutine thread: ${Thread.currentThread().name}")
            val result = fakeApiCall()
            println("Coroutine executed with result: $result")
            result
        }
        // default dispatcher에서 코루틴 실행
        // DefaultDispatcher-worker-1 @coroutine#2

        // 결과를 기다림과 더불어 값도 반환함
        val result = deferred.await()
        assertEquals(result, 42)
    }

    @Test
    fun testRunBlocking() = runBlocking {
        println("Main thread: ${Thread.currentThread().name}")

        // runBlocking 빌더를 사용하여 블록하는 코루틴 실행
        println("Coroutine thread: ${Thread.currentThread().name}")
        val result = fakeApiCall()
        // 코루틴스레드 = 메인스레드
        println("Coroutine executed with result: $result")
        assertEquals(result, 42)
    }

    @Test
    fun testWithContext() = runBlocking {
        println("Main thread: ${Thread.currentThread().name}")

        // withContext 빌더를 사용하여 특정한 디스패처에서 코루틴 실행
        println("Coroutine thread before: ${Thread.currentThread().name}")
        val result = withContext(Dispatchers.IO) {
            println("Coroutine thread inside: ${Thread.currentThread().name}")
            val result = fakeApiCall()
            println("Coroutine executed with result: $result")
            result
        }
        // DefaultDispatcher-worker-1 @coroutine#1
        println("Coroutine thread after: ${Thread.currentThread().name}")
        assertEquals(result, 42)
    }
}