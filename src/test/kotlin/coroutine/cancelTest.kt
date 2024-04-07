package coroutine

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class cancelTest {

    @Test
    fun `중단과 재개`() = runBlocking {

        val job = launch {
            suspendCoroutineTest().apply {
                println(this)
            }
        }

        val job2 = launch {
            suspendCancellableCoroutineTest().apply {
                println(this)
            }
        }

        delay(500)
        job.cancel()
        job2.cancel()
        // job2는 캔슬되고 job은 resume됨

//        delay(1500)
//        job.cancel()
//        job2.cancel()
        // 위 주석코드는, 둘다 resume됨
        println("test")
    }

    private suspend fun suspendCoroutineTest(): String = suspendCoroutine { continuation: Continuation<String> ->
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            continuation.resume("resume: suspendCoroutine!")
        }
    }


    private suspend fun suspendCancellableCoroutineTest(): String = suspendCancellableCoroutine { continuation: CancellableContinuation<String> ->
        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            continuation.resume("resume: suspendCancellableCoroutine!") {
                //onCancellation 존재
                println("cancelled: suspendCancellableCoroutine")
            }
        }
    }
}