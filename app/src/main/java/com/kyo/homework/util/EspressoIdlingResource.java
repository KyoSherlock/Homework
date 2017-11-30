package com.kyo.homework.util;

import android.support.test.espresso.IdlingResource;

/**
 * Created by jianghui on 2017/11/30.
 */

public class EspressoIdlingResource {


    private static final String RESOURCE = "GLOBAL";

    private static SimpleCountingIdlingResource mCountingIdlingResource =
            new SimpleCountingIdlingResource(RESOURCE);

    public static void increment() {
        mCountingIdlingResource.increment();
    }

    public static void decrement() {
        mCountingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }

}
