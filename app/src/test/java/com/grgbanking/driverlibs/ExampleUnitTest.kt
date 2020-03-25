package com.grgbanking.driverlibs

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*
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
    //sadddddddddddddddd
    }

        //我是从分支合并过来的
//<<<<<<< HEAD:app/src/test/java/com/grgbanking/driverlibsdemo/ExampleUnitTest.kt
//            }

//你dev更新一下
//=======
        //在合并到主线
            }

    @Test
    fun today(){
        val format = SimpleDateFormat("yyyy-MM-dd")
        val format1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date = format.parse("2020-03-18 16:43:00")
        var date1 = format.format(Date())
        println(date)
        println(date1)

        println(Date().after(date))
        println(format1.parse("2020-03-18 16:43:00") )
        println(Date())

    }

//>>>>>>> dev:app/src/test/java/com/grgbanking/driverlibs/ExampleUnitTest.kt
//}
