package com.grgbanking.driverlibs

import android.app.backup.BackupAgent
import com.sun.jna.platform.win32.Version
import com.sun.jna.platform.win32.WinDef
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
    fun List<String>.getShowtWordsTo(shortwords:MutableList<String>,maxLenth:Int){
        this.filterTo(shortwords){
            it.length<maxLenth
        }
        val artical =   setOf("a","A","an","An","the","The")
         shortwords-=artical

    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        ss()
    }

    inline fun <T> method(lock: Lock, noinline body: () -> T): T {
        lock.lock()
        try {
            otherMethod(body)
            return body()
        } finally {
            lock.unlock()

        }

    }

    fun <T> otherMethod(body: () -> T) {
        //sadddddddddddddddd
    }

    //我是从分支合并过来的
//<<<<<<< HEAD:app/src/test/java/com/grgbanking/driverlibsdemo/ExampleUnitTest.kt
//            }

//你dev更新一下
//=======
    //在合并到主线


    @Test
    fun today() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val format1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date = format.parse("2020-03-18 16:43:00")
        var date1 = format.format(Date())
        println(date)
        println(date1)

        println(Date().after(date))
        println(format1.parse("2020-03-18 16:43:00"))
        println(Date())

    }


    @Test
    fun ss() {
        var ss = mutableListOf(1, 2, 3)
        var str: String? = "null"
        var map = mapOf("1" to 1, "2" to 2)
        for ((k, v) in map) {
            println("$k:$v")
            ss.filter {
                it > 2
            }
                .map {
                    it + 3
                }.forEach {
                    println(it)
                }

        }
        println("-------------")
        println(ss.let {
            it.size
            99
        }
        )
        println(ss.also {
            it.size
            99
        })
        println(ss.apply {
            size
            99
        })
        println(ss.run {
            ss.size
            99
        })
        println(with(ss) {
            ss.size
            99
        })
        println(ss.takeIf{
            ss.size>3

        }!=null)
        println("-------------")
            kotlin.run {

            }
        when {
            str!!.isEmpty() -> println("isEmpty")
            else -> println("else")
        }
        var testb = testbean().apply {
            age = "99"
            name = "gong"
        }
        println(testb.name)
//        val a = D()
//        a.testopen()
//        a.a=5
//        println(a.stringRepresentation)
    }
//>>>>>>> dev:app/src/test/java/com/grgbanking/driverlibs/ExampleUnitTest.kt


    val repeatFun: String.(Int) -> String = {
            times -> this.repeat(times) }
    val twoParameters: (String, Int) -> String = repeatFun // OK

    fun runTransformation(f: (String, Int) -> String): String {
        return f("hello", 3)
    }
    @Test
   fun testSwap(){
        val result = runTransformation(repeatFun) // O
    println(result)
        var a =4
        var b = 5
        a=b.also { it ->
            b=a
        }
        println(a)
        println(b)
        foo(1) { println("hello") }     // 使用默认值 baz = 1
        foo(qux = { println("hello") }) // 使用两个默认值 bar = 0 与 baz = 1
        foo { println("hello") }
        "sad" tt 3*3

        val words = "A long time ago in a galaxy far far away".split(" ")
        val shortWords = mutableListOf<String>()
        words.getShowtWordsTo(shortWords, 3)
        println(shortWords)
       ( words.iterator().forEach {
           println(it)
       })

println("==================")
        val numbers1 = listOf("one", "two", "three", "four", "five")

        println(numbers1.groupBy {
            it.first().toUpperCase() })
        println(numbers1.groupBy(keySelector = { it.first() }, valueTransform = { it.toUpperCase() }))

        val numbers2 = listOf("one", "two", "three", "four", "five", "six")
        println(numbers2.slice(1..3))
        println(numbers2.slice(0..4 step 2))
        println(numbers2.slice(setOf(3, 5, 0)))
        println("take")
        println(numbers2.take(3))
        println(numbers2.takeLast(3))
        println(numbers2.drop(1))
        println(numbers2.dropLast(5))
        println("takeWhile")
        println(numbers2.takeWhile { !it.startsWith('f') })
        println(numbers2.takeLastWhile { it != "three" })
        println(numbers2.dropWhile { it.length == 3 })
        println(numbers2.dropLastWhile { it.contains('i') })
        var ss = (1..10).toList()
        println(ss.chunked(3))
        println(ss.windowed(3))

        val numbers4 = listOf("one", "two", "three", "four", "five")
        println(numbers4.zipWithNext())
        println(numbers4.zipWithNext() { s1, s2 -> s1.length > s2.length})
        val names = listOf("Alice Adamsqqqq", "Brian Brown", "Clara Campbell")
        println(names.associate {
                name -> name.let {
            it.length to it
        }
              })

        var enpty = emptyArray<String>()
        val map = mutableListOf("2",1,2,3)
            map.first{
                it.toString().length>0
            }

        var map1 = mapOf(1 to 1)

        map1.forEach {
            key,_value->
            println("key${key}:value${_value}")

        }
        val numbers = listOf("one", "two", "three", "four")
        println("find")
        var res = numbers.find {
            it.length<10
        }
        println(res)
        println("findover")
        val (match, rest) = numbers.partition { it.length > 3 }
        println(numbers.any{
            it.equals("one")
        })
        println(match)
        println(rest)
        map.associateWith {
            it        }
//        println(map.unzip())
    }
    fun foo(bar: Int = 0, baz: Int = 1, qux: () -> Unit) {
        println("bar$bar")
        println("baz$baz")
        println("qux${qux()}")
        /*……*/ }

infix fun String.tt(arg:Int){
    println(this.toString()+"$arg")
}

    class testbean() {
        var age: String = ""
        var name: String = ""
    }

    interface A {
        fun foo() { print("A") }
        fun bar()
    }

    interface B {
        fun foo() { print("B") }
        fun bar() { print("bar") }
    }

    class C : A {
        override fun bar() { print("bar") }
    }

    class D : A, B {
        override fun foo() {
            super<A>.foo()

        }

        override fun bar() {

        }
    }

}
