package com.example.spff.coroutineexample;

public interface LongRunningCallbackOne {

    interface OneCallback {
        void callback(Integer i);
    }

    void one(OneCallback oneCallback);

}
