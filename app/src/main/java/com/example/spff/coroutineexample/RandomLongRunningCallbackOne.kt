package com.example.spff.coroutineexample

import java.util.*

/**
 * @param min unit: millisecond, inclusive, should >= 0
 * @param max unit: millisecond, exclusive, should >= min
 * */
class RandomLongRunningCallbackOne(private val min: Int, private val max: Int) : LongRunningCallbackOne {

    private val random = Random()

    override fun one(oneCallback: LongRunningCallbackOne.OneCallback) {

        Thread.sleep(random.nextInt(max - min) + min.toLong())
        oneCallback.callback(1)
    }
}
