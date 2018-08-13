package com.example.spff.coroutineexample


import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlin.coroutines.experimental.suspendCoroutine
import kotlin.system.measureTimeMillis


class AccumulationKotlin(private val longRunningCallbackOne: LongRunningCallbackOne) : Accumulation, AnkoLogger {

    override fun accumulate(times: Int) {

        suspend fun blockingOne(): Int = suspendCoroutine { continuation ->
            longRunningCallbackOne.one { continuation.resume(it) }
        }

        val time = measureTimeMillis {
            val theList = List(times + 1) { _ -> async { blockingOne() } }
            runBlocking {
                info { "The answer is ${theList.fold(0) { a, b -> a + b.await() }}" }
            }
        }
        info { "Kotlin Completed in $time ms" }
    }

}
