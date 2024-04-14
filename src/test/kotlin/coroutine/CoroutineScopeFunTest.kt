package coroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class CoroutineScopeFunTest {

    /*
    *
    *  coroutineScope는 부모에게서 코루틴 콘텍스트를 상속받지만, Job은 따로 오버라이딩합니다. 따라서 구조화된 동시성을 제공합니다.
    * 부모로부터 컨텍스트를 상속받습니다.
    * 자신의 작업이 끝내기 전까지 모든 자식을 기다립니다.
    * 부모가 취소되면 자식들 모두 취소합니다.
    * */
    @Test
    fun `coroutine scope func`() = runBlocking {
//새로운 코루틴 스코프를 시작하는 중단 함수이며, 인자로 들어온 함수가 생성한 값을 반환합니다. (let과 비슷하게 동작)
// https://velog.io/@cksgodl/Kotlin-Coroutines-Deep-Dive-Chapter-02.-%EC%BD%94%ED%8B%80%EB%A6%B0-%EC%BD%94%EB%A3%A8%ED%8B%B4-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-11%EC%9E%A5-14%EC%9E%A5


        println("계산 중..")
        val a = coroutineScope {
            delay(1000)
            10
        }
        val b = coroutineScope {
            delay(1000)
            20
        }
        println(a)
        println(b)
    }

    @Test
    fun `join() 의 유무에 따른 순서 차이`() = runBlocking {


        val loggingJob = launch {
            delay(1000)
            println("log >> logging test ")
        }

        loggingJob.join()
        // join이 없으면 end->test
        // join이 있으면 test->end
        // join:: Suspends the coroutine until this job is complete.
        // 즉, join은 코루틴을 기다리게 하는것이지 해당 잡의 실행 유무를 결정하는게 아님!
        // 로깅이나 모니터링 등 꼭 기다려야할 필요 없는건 독립적으로 작업가능. 이런건 join 필요없는 것?
        println("end")
    }
}