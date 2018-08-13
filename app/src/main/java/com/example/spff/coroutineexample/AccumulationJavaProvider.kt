package com.example.spff.coroutineexample

class AccumulationJavaProvider(private val longRunningCallbackOne: LongRunningCallbackOne) : AccumulationProvider {
    override fun provideAddition(): Accumulation {
        return AccumulationJava(longRunningCallbackOne)
    }
}
