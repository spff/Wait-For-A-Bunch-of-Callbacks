package com.example.spff.coroutineexample


class ConstantLongRunningCallbackOne(private val ms: Long) : LongRunningCallbackOne {

    override fun one(oneCallback: LongRunningCallbackOne.OneCallback) {
        Thread.sleep(ms)
        oneCallback.callback(1)
    }
}
