package com.example.spff.coroutineexample


interface Accumulation {
    /**
     * @param times should be >= 1
     */
    fun accumulate(times: Int)
}