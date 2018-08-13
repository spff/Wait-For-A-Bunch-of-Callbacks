package com.example.spff.coroutineexample

class ConstantLongRunningCallbackOneProvider : LongRunningCallbackOneProvider {
    override fun provideLongRunningCallbackOne(): LongRunningCallbackOne {
        return ConstantLongRunningCallbackOne(500)
    }

}