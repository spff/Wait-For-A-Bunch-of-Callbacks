package com.example.spff.coroutineexample

class RandomLongRunningCallbackOneProvider : LongRunningCallbackOneProvider {
    override fun provideLongRunningCallbackOne(): LongRunningCallbackOne {
        return RandomLongRunningCallbackOne(500, 1500)
    }

}