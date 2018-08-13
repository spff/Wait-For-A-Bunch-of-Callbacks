package com.example.spff.coroutineexample;

import android.support.annotation.NonNull;

interface AccumulationProvider {
    @NonNull
    Accumulation provideAddition();
}
