package com.example.spff.coroutineexample

class AccumulationKotlinProvider(private val longRunningCallbackOne: LongRunningCallbackOne) : AccumulationProvider {

    override fun provideAddition(): Accumulation {
        return AccumulationKotlin(longRunningCallbackOne)
    }
}
