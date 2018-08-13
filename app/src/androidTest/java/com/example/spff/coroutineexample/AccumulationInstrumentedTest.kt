package com.example.spff.coroutineexample

import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccumulationInstrumentedTest {

    @Test
    fun accumulationTest() {
        AccumulationKotlinProvider(ConstantLongRunningCallbackOneProvider().provideLongRunningCallbackOne()).provideAddition().accumulate(119)
        AccumulationJavaProvider(ConstantLongRunningCallbackOneProvider().provideLongRunningCallbackOne()).provideAddition().accumulate(119)
    }

}
