package com.grgbanking.driverlibs

import org.junit.Test

import org.junit.Assert.*
import java.util.concurrent.locks.Lock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    inline fun <T> method(lock: Lock, noinline body: () -> T): T {
            lock.lock()
        try {
            otherMethod(body)
            return body()
        }finally {
            lock.unlock()

        }

    }
    fun <T>otherMethod(body:() -> T){

        //我是从分支合并过来的
        //在合并到主线
            }
}
