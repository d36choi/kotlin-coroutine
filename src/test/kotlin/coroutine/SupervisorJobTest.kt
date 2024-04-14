package coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.lang.Exception

class SupervisorJobTest {


    @Test
    fun `자식에서의 예외를 무시하려면 SupervisorJob`() = runBlocking {
        val scope = CoroutineScope(SupervisorJob())
        // 일반 Job()이면 출력되지않음

        scope.launch {
            delay(1000)
            throw Exception("test")
        }


        scope.launch {
            delay(2000)
            println("success after 2sec")
        }

        delay(3000)

    }
    @Test
    fun `자식에서의 예외를 무시하려면 supervisorScope`() = runBlocking {
        supervisorScope {
            // coroutineScope와 동일하게 새로운 스코프에서 코루틴을 시작하지만, SupervisorJob의 기능을 상속한다고 보면 됩니다

            launch {
                delay(1000)
                throw Exception("test")
            }


            launch {
                delay(2000)
                println("success after 2sec")
            }
        }

        delay(1000)
        println("done")

    }

    @Test
    fun `예외 핸들링을 하려면 CoroutineExceptionHandler를 컨텍스트에 결합`() = runBlocking {
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("coroutineContext : ${coroutineContext.job} and exception : ${throwable.message}")
        }

        val scope = CoroutineScope(SupervisorJob() + handler)

        scope.launch {
            delay(1000L)
            throw Exception("test")
        }
        scope.launch {
            delay(2000)
            println("success after 2sec")
        }

        delay(3000)
    }
}